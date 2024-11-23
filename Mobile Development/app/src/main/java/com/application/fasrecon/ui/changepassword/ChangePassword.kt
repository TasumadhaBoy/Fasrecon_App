package com.application.fasrecon.ui.changepassword

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowInsetsController
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityChangePasswordBinding
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.ui.forgotpassword.ForgotPassword
import com.application.fasrecon.ui.register.RegisterActivity

class ChangePassword : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        setSpannable(binding.newPasswordFormat)

        binding.changeBtn.setOnClickListener(this)
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

                finish()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}