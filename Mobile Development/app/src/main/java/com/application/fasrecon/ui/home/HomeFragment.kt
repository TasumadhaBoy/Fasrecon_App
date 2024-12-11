package com.application.fasrecon.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.data.remote.response.Label
import com.application.fasrecon.databinding.FragmentHomeBinding
import com.application.fasrecon.ui.chatbot.ChatbotActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.getImageUri
import com.application.fasrecon.util.reduceFileImage
import com.application.fasrecon.util.uriToFile
import com.bumptech.glide.Glide
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var imageUri: Uri? = null
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactoryUser.getInstance(
            requireActivity()
        )
    }

    private val openAccessCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                startCrop(imageUri)

            } else {
                imageUri = null
                showToast(getString(R.string.failed_take_photo))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getClothesTotal().observe(requireActivity()) {
            binding?.infoClothesCollection?.text = getString(R.string.clothes_user, it.toString())
        }

        binding?.cardContainerCamera?.setOnClickListener {
            openCamera()
        }

        binding?.cardContainerChatbot?.setOnClickListener {
            startActivity(Intent(requireActivity(), ChatbotActivity::class.java))
        }

        homeViewModel.getUserData().observe(requireActivity()) {
           setUserData(it)
        }

        homeViewModel.loadingData.observe(requireActivity()) {
            displayLoading(it)
        }

        homeViewModel.errorHandling.observe(requireActivity()) {
            it.getDataIfNotDisplayed()?.let { msg ->
                SweetAlertDialog(requireActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.classify_image_failed))
                    .setConfirmText(getString(R.string.try_again))
                    .setContentText(msg)
                    .show()
            }
        }

        homeViewModel.classifyResult.observe(viewLifecycleOwner) { label ->
            Log.d("mantap" , label.toString())
            imageUri?.let { insertClothes(imageUri, label) }
        }
    }

    private fun setUserData(userData: UserEntity) {
        binding?.tvUserHomepage?.text = getString(R.string.hai_user, userData.name)
        if (userData.photoUrl != null) {
            binding?.imageProfileHomepage?.let {
                Glide.with(this)
                    .load(userData.photoUrl)
                    .error(R.drawable.no_profile)
                    .into(it)
            }
        }
    }

    private fun openCamera() {
        imageUri = context?.let { getImageUri(it) }
        imageUri?.let { openAccessCamera.launch(imageUri) }
    }

    private fun startCrop(uri: Uri?) {
        val imageFile = "${System.currentTimeMillis()}.jpg"
        val uriFile = Uri.fromFile(File(requireActivity().cacheDir, imageFile))
        val uCropIntent = uri?.let {
            UCrop.of(it, uriFile)
                .withMaxResultSize(2000, 2000)
                .getIntent(requireContext())
        }

        if (uCropIntent != null) {
            startActivityForResult(uCropIntent, UCrop.REQUEST_CROP)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            data?.let { imageUri = UCrop.getOutput(data) }
            imageUri?.let { classifyImage(it) }

        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = data?.let { UCrop.getError(data) }
            showToast(cropError?.message ?: getString(R.string.crop_image_failed))
        }
    }

    private fun classifyImage(imageUri: Uri) {
        val imgFile = uriToFile(imageUri, requireContext()).reduceFileImage()
        val reqImageFile = imgFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData("images", imgFile.name, reqImageFile)
        homeViewModel.classifyImage(multipartBody)
    }

    private fun insertClothes(imageUriUpload: Uri?, label: Label){
        homeViewModel.insertClothes(imageUriUpload.toString(), label.type, label.color)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.loadingClassifyHome?.visibility = View.VISIBLE
        } else {
            binding?.loadingClassifyHome?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}