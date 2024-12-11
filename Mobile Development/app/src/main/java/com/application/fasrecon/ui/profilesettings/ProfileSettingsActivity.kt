package com.application.fasrecon.ui.profilesettings

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.data.local.entity.UserEntity
import com.application.fasrecon.databinding.ActivityProfileSettingsBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.ui.changepassword.ChangePasswordActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser
import com.application.fasrecon.util.WrapMessage
import com.bumptech.glide.Glide

class ProfileSettingsActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileSettingsBinding
    private val profileSettingsViewModel: ProfileSettingsViewModel by viewModels { ViewModelFactoryUser.getInstance(this) }
    private var imageUriSelected: Uri? = null
    private var name: String? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        profileSettingsViewModel.getUserData().observe(this) {
            setUserData(it)
        }

        binding.nameEditInputLayout.setEndIconOnClickListener {
            binding.nameEditInputLayout.isEnabled = true
            binding.nameEditInput.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.nameEditInput, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.profileSettingsPage.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                disabledText()
            }
            false
        }

        binding.changePassword.setOnClickListener {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intent)
        }

        binding.saveBtn.setOnClickListener {
            updateUser()
        }

        binding.containerChangeImage.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun disabledText() {
        binding.nameEditInput.isEnabled = false
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.nameEditInput.windowToken, 0)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = PhotoGalleryBottomSheetDialog()
        bottomSheetDialog.setImageByUser(object: PhotoGalleryBottomSheetDialog.ImageByUser {
            override fun imageUriByUser(imageUri: Uri) {
                showImage(imageUri)
                imageUriSelected = imageUri
            }
        })
        bottomSheetDialog.show(
            supportFragmentManager,
            PhotoGalleryBottomSheetDialog.PHOTO_CAMERA_BOTTOM_SHEET_DIALOG
        )
    }

    private fun setUserData(userData: UserEntity) {
        name = userData.name
        binding.nameEditInput.setText(userData.name)
        binding.emailEditInput.setText(userData.email)
        binding.passEditInput.setText(userData.password)
        if (userData.photoUrl != null) {
            Glide.with(this)
                .load(userData.photoUrl)
                .error(R.drawable.no_profile)
                .into(binding.imageProfileSettings)
        }
    }

    private fun showImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .error(R.drawable.no_profile)
            .into(binding.imageProfileSettings)
    }

    private fun updateUser() {
        val newName = binding.nameEditInput.text.toString().trim()
        if ((newName != name && imageUriSelected != null) || (newName == name && imageUriSelected != null)) {
            profileSettingsViewModel.updateData(newName, imageUriSelected)
        } else if (newName != name && imageUriSelected == null) {
            profileSettingsViewModel.updateData(newName)
        }  else {
            moveToMainActivity()
        }

        profileSettingsViewModel.loadingData.observe(this) {
            displayLoading(it)
        }

        profileSettingsViewModel.errorUpdateHandling.observe(this) {
            it.getDataIfNotDisplayed()?.let { msg ->
                val message = when(msg) {
                    "NO_INTERNET" -> getString(R.string.no_internet)
                    "TOO_MANY_REQUEST" -> getString(R.string.too_many_request)
                    "LOGIN_AGAIN" -> getString(R.string.failed_update_login_again)
                    else -> msg
                }

                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.update_profil_failed))
                    .setConfirmText("Try Again")
                    .setContentText(message)
                    .show()
            }
        }

        profileSettingsViewModel.updateDataMessage.observe(this) {
            Log.d("Test", imageUriSelected.toString())
            profileSettingsViewModel.updateUserDataLocal(newName, imageUriSelected.toString())
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Update Profile Success")
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    moveToMainActivity()
                }
                .show()
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingUpdate.visibility = View.VISIBLE
        } else {
            binding.loadingUpdate.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}