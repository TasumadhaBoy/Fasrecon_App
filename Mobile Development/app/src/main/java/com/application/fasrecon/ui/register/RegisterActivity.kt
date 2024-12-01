package com.application.fasrecon.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityRegisterBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.login.LoginActivity

class RegisterActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnRegister.setOnClickListener(this)
        binding.loginNavigation.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_register -> {
                val username = binding.textInputUsername.text.toString()
                val email = binding.textInputEmail.text.toString()
                val password = binding.textInputPassword.text.toString()

                if (username.isEmpty()) {
                    binding.textInputUsername.setError(
                        resources.getString(R.string.field_username),
                        null
                    )
                    binding.textInputUsername.requestFocus()
                    return
                }

                if (email.isEmpty()) {
                    binding.textInputEmail.setError(
                        resources.getString(R.string.field_email),
                        null
                    )
                    binding.textInputEmail.requestFocus()
                    return
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() && email.isNotBlank()
                ) {
                    binding.textInputEmail.setError(
                        resources.getString(R.string.format_email),
                        null
                    )
                    return
                }
                if (password.isEmpty()) {
                    binding.textInputPassword.setError(
                        resources.getString(R.string.field_password),
                        null
                    )
                    binding.textInputPassword.requestFocus()
                    return
                }
                if (password.length < 8) {
                    binding.textInputPassword.setError(
                        resources.getString(R.string.format_password),
                        null
                    )
                    binding.textInputPassword.requestFocus()
                    return
                }
            }
            R.id.login_navigation -> {
                val moveToLoginPage = Intent(this, LoginActivity::class.java)
                moveToLoginPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(moveToLoginPage)
            }
        }
    }
}