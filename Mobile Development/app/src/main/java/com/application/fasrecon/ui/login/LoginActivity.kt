package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityLoginBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.register.RegisterActivity
import com.application.fasrecon.ui.viewmodelfactory.ViewModelFactoryAuth
import com.application.fasrecon.util.WrapMessage

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactoryAuth.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
        binding.registerNavigation.setOnClickListener(this)

        loginViewModel.loadingData.observe(this) {
            displayLoading(it)
        }

        loginViewModel.errorHandling.observe(this) {
            it.getDataIfNotDisplayed()?.let { msg ->
                val message = when(msg) {
                    "NO_INTERNET" -> getString(R.string.no_internet)
                    "WRONG_EMAIL_PASSWORD" -> getString(R.string.wrong_email_password)
                    "INVALID_USER" -> getString(R.string.invalid_user)
                    "TOO_MANY_REQUEST" -> getString(R.string.too_many_request)
                    else -> msg
                }

                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Login Failed")
                    .setConfirmText("Try Again")
                    .setContentText(message)
                    .show()
            }
        }

        loginViewModel.loginMessage.observe(this) {
            val password = binding.passwordLoginInput.text.toString().trim()
            loginViewModel.saveSession(password)
            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Login Success")
                .setContentText("Welcome $it")
                .setConfirmClickListener { sDialog ->
                    sDialog.dismissWithAnimation()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .show()
        }

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

                loginViewModel.loginAccount(email, password)
            }

            R.id.register_navigation -> {
                val moveToRegisterPage =
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                moveToRegisterPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(moveToRegisterPage)
            }
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingLogin.visibility = View.VISIBLE
        } else {
            binding.loadingLogin.visibility = View.GONE
        }
    }
}