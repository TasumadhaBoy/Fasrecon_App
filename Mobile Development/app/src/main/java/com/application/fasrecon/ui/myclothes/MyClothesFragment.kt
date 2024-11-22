package com.application.fasrecon.ui.myclothes

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.fasrecon.R
import com.application.fasrecon.databinding.FragmentMyclothesBinding

class MyClothesFragment : Fragment() {

    private var _binding: FragmentMyclothesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyclothesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.filterOutfit.setOnClickListener {
            showFilterDialog()
        }
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

        val list = listOf("T-shirt", "Shirt", "jacket", "Hoodie", "Polo Shirt", "Trucker")
        val clothesList = dialog.findViewById<RecyclerView>(R.id.list_outfit_type)
        val adapter = setAllClothesType(list, clothesList)
        val layoutManager = LinearLayoutManager(requireActivity())
        clothesList.layoutManager = layoutManager

        dialog.findViewById<Button>(R.id.btn_cancel_filter).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_apply_filter).setOnClickListener {
            val selectedItem = adapter.getSelectedItem()
            if (selectedItem != null) {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setAllClothesType(list: List<String>, recyclerView: RecyclerView): MyClothesTypeAdapter{
        val adapter = MyClothesTypeAdapter()
        adapter.submitList(list)
        recyclerView.adapter = adapter
        return adapter
    }

    private fun setListClothes(list: List<String>, recyclerView: RecyclerView): MyClothesTypeAdapter{
        val adapter = MyClothesTypeAdapter()
        adapter.submitList(list)
        recyclerView.adapter = adapter
        return adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}