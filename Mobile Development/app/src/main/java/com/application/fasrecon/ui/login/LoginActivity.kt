package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.fasrecon.MainActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)

        supportActionBar?.hide()

        val text = "Belum Punya akun? Daftar"
        val spannableString = SpannableString(text)

        val blueColor = ContextCompat.getColor(this, R.color.custom_blue)
        val blueColorSpan = ForegroundColorSpan(blueColor)
        spannableString.setSpan(blueColorSpan, 18, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val moveToRegisterPage = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(moveToRegisterPage)
            }

            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = blueColor
            }
        }
        spannableString.setSpan(clickableSpan, 18, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.textView.text = spannableString
        binding.textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }

    override fun onClick(p0: View?) {
        if (p0?.id == R.id.forgot_password) {
            val moveToForgotPasswordPage = Intent(this@LoginActivity, ForgotPassword::class.java)
            startActivity(moveToForgotPasswordPage)
        }
        else if (p0?.id == R.id.btn_login) {
            val moveMainActivityPage = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveMainActivityPage)
        }

    }
}