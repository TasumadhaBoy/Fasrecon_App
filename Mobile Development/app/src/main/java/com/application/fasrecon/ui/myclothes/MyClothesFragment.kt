package com.application.fasrecon.ui.myclothes

import android.app.Dialog
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.ClothesEntity
import com.application.fasrecon.data.remote.response.Label
import com.application.fasrecon.databinding.FragmentMyclothesBinding
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.reduceFileImage
import com.application.fasrecon.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class MyClothesFragment : Fragment() {

    private var _binding: FragmentMyclothesBinding? = null
    private val binding get() = _binding
    private var imageUriUpload: Uri? = null
    private val myClothesViewModel: MyClothesViewModel by viewModels { ViewModelFactoryUser.getInstance(requireActivity()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyclothesBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()

        myClothesViewModel.loadingData.observe(viewLifecycleOwner) {
            displayLoading(it)
        }

        myClothesViewModel.errorHandling.observe(viewLifecycleOwner) {
            it.getDataIfNotDisplayed()?.let { msg ->
                SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.classify_image_failed))
                    .setConfirmText(getString(R.string.try_again))
                    .setContentText(msg)
                    .show()
            }
        }

        myClothesViewModel.getAllClothes().observe(viewLifecycleOwner) {
            setListClothes(it, binding?.listClothes)
            val orientation = resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                val layoutManager = LinearLayoutManager(requireActivity())
                binding?.listClothes?.layoutManager = layoutManager
            } else {
                val layoutManager = GridLayoutManager(requireActivity(), 2)
                binding?.listClothes?.layoutManager = layoutManager
            }
        }

        binding?.filterClothes?.setOnClickListener {
            showFilterDialog()
        }

        binding?.addClothesButton?.setOnClickListener {
            showBottomSheetDialog()
        }

        myClothesViewModel.classifyResult.observe(viewLifecycleOwner) { label ->
            imageUriUpload?.let { insertClothes(imageUriUpload, label) }
        }
    }

    private fun setActionBar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding?.topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title =
            getString(R.string.my_clothes)
    }

    private fun showFilterDialog() {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.filter_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        myClothesViewModel.getClothesType().observe(viewLifecycleOwner) {
            val clothesList = dialog.findViewById<RecyclerView>(R.id.list_clothes_type)
            val adapter = setAllClothesType(it, clothesList)
            val layoutManager = LinearLayoutManager(requireContext())
            clothesList.layoutManager = layoutManager

            dialog.findViewById<Button>(R.id.btn_cancel_filter).setOnClickListener {
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btn_apply_filter).setOnClickListener {
                val selectedItem = adapter.getSelectedItem()
                if (selectedItem != null) {
                    myClothesViewModel.getAllClothesByType(selectedItem).observe(viewLifecycleOwner) { list ->
                        setListClothes(list, binding?.listClothes)
                    }
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = AddClothesBottomSheetDialog()
        bottomSheetDialog.setImageByUser(object: AddClothesBottomSheetDialog.ImageByUser {
            override fun imageUriByUser(imageUri: Uri) {
                classifyImage(imageUri)
                imageUriUpload = imageUri
            }
        })
        bottomSheetDialog.show(
            parentFragmentManager,
            AddClothesBottomSheetDialog.ADD_CLOTHES_BOTTOM_SHEET_DIALOG
        )
    }

    private fun classifyImage(imageUri: Uri) {
        val imgFile = uriToFile(imageUri, requireContext()).reduceFileImage()
        val reqImageFile = imgFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("images", imgFile.name, reqImageFile)
        myClothesViewModel.classifyImage(multipartBody)
    }

    private fun insertClothes(imageUriUpload: Uri?, label: Label){
        myClothesViewModel.insertClothes(imageUriUpload.toString(), label.type, label.color)
    }

    private fun setAllClothesType(
        list: List<String>,
        recyclerView: RecyclerView?
    ): MyClothesTypeAdapter {
        val adapter = MyClothesTypeAdapter()
        adapter.submitList(list)
        recyclerView?.adapter = adapter
        return adapter
    }

    private fun setListClothes(
        list: List<ClothesEntity>,
        recyclerView: RecyclerView?
    ) {
        val adapter = MyClothesListAdapter { deleteClothes ->
            deleteDialog(deleteClothes)
        }

        adapter.submitList(list)
        recyclerView?.adapter = adapter
    }

    private fun deleteDialog(clothes: ClothesEntity) {
        SweetAlertDialog(requireActivity(), SweetAlertDialog.WARNING_TYPE)
            .setTitleText(getString(R.string.delete_clothes))
            .setContentText(getString(R.string.confirmation_delete))
            .setConfirmText(getString(R.string.yes))
            .setCancelText(getString(R.string.no))
            .setConfirmClickListener { sDialog ->
                myClothesViewModel.deleteClothes(clothes.id)
                sDialog.dismissWithAnimation()
            }
            .show()
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.loadingClassifyImage?.visibility = View.VISIBLE
        } else {
            binding?.loadingClassifyImage?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}