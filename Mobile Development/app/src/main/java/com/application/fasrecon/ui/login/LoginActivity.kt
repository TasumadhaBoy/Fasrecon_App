package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityLoginBinding
import com.application.fasrecon.ui.forgotpassword.ForgotPassword
import com.application.fasrecon.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.registerNavigation.setOnClickListener(this)
        binding.forgotPassword.setOnClickListener(this)

        supportActionBar?.hide()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                val email = binding.emailLoginInput.text.toString().trim()
                val password = binding.passwordLoginInput.text.toString().trim()

                if (email.isEmpty()) {
                    binding.emailLoginInput.setError(
                        resources.getString(R.string.field_email),
                        null
                    )
                    binding.emailLoginInput.requestFocus()
                    return
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() && email.isNotBlank()
                ) {
                    binding.emailLoginInput.setError(
                        resources.getString(R.string.format_email),
                        null
                    )
                    binding.emailLoginInput.requestFocus()
                    return
                }
                if (password.isEmpty()) {
                    binding.passwordLoginInput.setError(
                        resources.getString(R.string.field_password),
                        null
                    )
                    binding.passwordLoginInput.requestFocus()
                    return
                }
                if (password.length < 8) {
                    binding.passwordLoginInput.setError(
                        resources.getString(R.string.format_password),
                        null
                    )
                    binding.passwordLoginInput.requestFocus()
                    return
                }

                // loginViewModel.loginAccount(email, password)
                val moveToLoginPage =
                    Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(moveToLoginPage)
            }

            R.id.register_navigation -> {
                val moveToRegisterPage =
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(moveToRegisterPage)
            }

            R.id.forgot_password -> {
                val moveToForgotPasswordPage =
                    Intent(this@LoginActivity, ForgotPassword::class.java)
                startActivity(moveToForgotPasswordPage)
            }
        }
    }
}