package com.application.fasrecon.ui.myoutfit

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.fasrecon.R
import com.application.fasrecon.databinding.FragmentMyoutfitBinding

class MyoutfitFragment : Fragment() {

    private var _binding: FragmentMyoutfitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMyoutfitBinding.inflate(inflater, container, false)
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
        val clothList = dialog.findViewById<RecyclerView>(R.id.list_outfit_type)
        setAllClothType(list, clothList)
        val layoutManager = LinearLayoutManager(requireActivity())
        clothList.layoutManager = layoutManager

        dialog.findViewById<Button>(R.id.btn_cancel_filter).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btn_apply_filter).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setAllClothType(list: List<String>, recyclerView: RecyclerView){
        val adapter = ClothTypeAdapter()
        adapter.submitList(list)
        recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}