package com.application.fasrecon.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityForgotPasswordBinding
import com.application.fasrecon.ui.login.LoginActivity

class ForgotPassword : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.btnGanti.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.btn_ganti) {
            val moveToLoginPage = Intent(this@ForgotPassword, LoginActivity::class.java)
            startActivity(moveToLoginPage)
        }
    }
}