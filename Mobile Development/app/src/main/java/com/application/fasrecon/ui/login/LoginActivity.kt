package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.application.fasrecon.ui.MainActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityLoginBinding
import com.application.fasrecon.ui.BaseActivity
import com.application.fasrecon.ui.forgotpassword.ForgotPassword
import com.application.fasrecon.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private var auth = FirebaseAuth.getInstance()

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


                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login Success")
                                .setContentText("Welcome ${auth.currentUser?.displayName}")
                                .setConfirmClickListener { sDialog ->
                                    sDialog.dismissWithAnimation()
                                    val intent = Intent(this, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                }
                                .show()
                        } else {
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Failed")
                                .setConfirmText("Try Again")
                                .setContentText("Login Failed")
                                .show()
                        }
                    }

                // loginViewModel.loginAccount(email, password)
            }

            R.id.register_navigation -> {
                val moveToRegisterPage =
                    Intent(this@LoginActivity, RegisterActivity::class.java)
                moveToRegisterPage.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
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