package com.application.fasrecon.ui.changepassword

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityChangePasswordBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryUser

class ChangePasswordActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangePasswordBinding
    private val changePasswordViewModel: ChangePasswordViewModel by viewModels {
        ViewModelFactoryUser.getInstance(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        setSpannable(binding.newPasswordFormat)

        binding.changeBtn.setOnClickListener(this)

        changePasswordViewModel.loadingData.observe(this) {
            displayLoading(it)
        }

        changePasswordViewModel.errorHandling.observe(this) {
            it.getDataIfNotDisplayed()?.let { msg ->
                val message = when (msg) {
                    "NO_INTERNET" -> getString(R.string.no_internet)
                    "TOO_MANY_REQUEST" -> getString(R.string.too_many_request)
                    "LOGIN_AGAIN" -> getString(R.string.failed_update_login_again)
                    "INCORRECT_PASSWORD" -> getString(R.string.current_password_incorrect)
                    else -> msg
                }

                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.change_password_failed))
                    .setConfirmText(getString(R.string.try_again))
                    .setContentText(message)
                    .show()
            }

        }

        changePasswordViewModel.changePasswordMessage.observe(this) {

            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.change_password))
                .setConfirmText("Ok")
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    finish()
                }
                .show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.change_btn -> {
                val currentPassword = binding.currentPasswordInput.text.toString().trim()
                val newPassword = binding.newPasswordInput.text.toString().trim()

                if (currentPassword.isEmpty()) {
                    binding.currentPasswordInput.setError(
                        resources.getString(R.string.field_email),
                        null
                    )
                    binding.currentPasswordInput.requestFocus()
                    return
                }
                if (currentPassword.length < 8) {
                    binding.currentPasswordInput.setError(
                        resources.getString(R.string.format_password),
                        null
                    )
                    binding.newPasswordInput.requestFocus()
                    return
                }

                if (newPassword.isEmpty()) {
                    binding.newPasswordInput.setError(
                        resources.getString(R.string.field_password),
                        null
                    )
                    binding.newPasswordInput.requestFocus()
                    return
                }
                if (newPassword.length < 8) {
                    binding.newPasswordInput.setError(
                        resources.getString(R.string.format_password),
                        null
                    )
                    binding.newPasswordInput.requestFocus()
                    return
                }

                changePasswordViewModel.changePassword(currentPassword, newPassword)
            }
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setSpannable(view: TextView) {
        val targetView = view.text.toString().trim()
        val spannable = SpannableString(targetView)
        spannable.setSpan(ForegroundColorSpan(Color.RED), 0, 1, 0)
        view.text = spannable
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingChangePassword.visibility = View.VISIBLE
        } else {
            binding.loadingChangePassword.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}