package com.application.fasrecon.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.application.fasrecon.MainActivity
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val text = "Sudah punya akun? Login"
        val spannableString = SpannableString(text)

        val blueColor = ContextCompat.getColor(this, R.color.custom_blue)
        val blueColorSpan = ForegroundColorSpan(blueColor)
        spannableString.setSpan(blueColorSpan, 18, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val moveToMainActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(moveToMainActivity)
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
        binding.btnDaftar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (p0?.id == binding.btnDaftar.id) {
            Toast.makeText(this@RegisterActivity, "Daftar Berhasil", Toast.LENGTH_SHORT).show()
            val moveToMainActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(moveToMainActivity)
        }
    }
}
