package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.MainActivity
import com.application.fasrecon.databinding.ActivityLoginBinding
import com.application.fasrecon.ui.forgotpassword.ForgotPassword
import com.application.fasrecon.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener {
            val moveToForgotPasswordPage = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(moveToForgotPasswordPage)
        }
        binding.btnLogin.setOnClickListener {
            val moveToLoginPage = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveToLoginPage)
        }
        binding.registerNavigation.setOnClickListener {
            val moveToRegisterPage = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveToRegisterPage)
        }

        supportActionBar?.hide()
    }
}