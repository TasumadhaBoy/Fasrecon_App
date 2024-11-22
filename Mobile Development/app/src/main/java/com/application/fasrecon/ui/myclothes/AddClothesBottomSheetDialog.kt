package com.application.fasrecon.ui.myclothes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.fasrecon.databinding.BottomSheetDialogAddClothesBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddClothesBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetDialogAddClothesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =  BottomSheetDialogAddClothesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { dialogInterface ->
            val mDialog = dialogInterface as BottomSheetDialog
            val bottomSheetDialog = mDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetDialog?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = 50
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val ADD_CLOTHES_BOTTOM_SHEET_DIALOG = "ADD_CLOTHES_BOTTOM_SHEET_DIALOG"
    }
}