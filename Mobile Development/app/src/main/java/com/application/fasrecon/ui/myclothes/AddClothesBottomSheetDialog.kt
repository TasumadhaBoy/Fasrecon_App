package com.application.fasrecon.ui.myclothes

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.application.fasrecon.R
import com.application.fasrecon.databinding.BottomSheetDialogPhotoCameraBinding
import com.application.fasrecon.util.getImageUri
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yalantis.ucrop.UCrop
import java.io.File

@Suppress("DEPRECATION")
class AddClothesBottomSheetDialog: BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetDialogPhotoCameraBinding
    private var imageByUser: ImageByUser? = null
    private var imageUri: Uri? = null


    private val openGalleryPhoto = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri: Uri? ->
        if (uri != null){
            startCrop(uri)
        } else {
            showToast(getString(R.string.no_image))
        }
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding =  BottomSheetDialogPhotoCameraBinding.inflate(inflater, container, false)

        binding.galleryAddClothes.setOnClickListener {
            openGallery()
        }

        binding.takePictureAddClothes.setOnClickListener {
            openCamera()
        }

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

    private fun openGallery() {
        openGalleryPhoto.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun openCamera() {
        imageUri = context?.let { getImageUri(it) }
        openAccessCamera.launch(imageUri!!)
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
            imageUri = UCrop.getOutput(data!!)
            imageUri?.let { imageByUser?.imageUriByUser(it) }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast(cropError?.message ?: getString(R.string.crop_image_failed))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    fun setImageByUser(imageByUser: ImageByUser) {
        this.imageByUser = imageByUser
    }

    interface ImageByUser {
        fun imageUriByUser(imageUri: Uri)
    }

    companion object {
        const val ADD_CLOTHES_BOTTOM_SHEET_DIALOG = "ADD_CLOTHES_BOTTOM_SHEET_DIALOG"
    }
}