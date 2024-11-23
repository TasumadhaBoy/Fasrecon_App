package com.application.fasrecon.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.fasrecon.databinding.ActivityProfileSettingsBinding
import com.application.fasrecon.ui.MainActivity

class ProfileSettings : AppCompatActivity() {

    private lateinit var binding: ActivityProfileSettingsBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.topAppBar)

        binding.nameEditInputLayout.setEndIconOnClickListener {
            binding.nameEditInputLayout.isEnabled = true
            binding.nameEditInput.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.nameEditInput, InputMethodManager.SHOW_IMPLICIT)
        }

        binding.profileSettingsPage.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                disabledText()
            }
            false
        }


        binding.nameEditInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.nameEditInput.isEnabled = false
            }
        }

        binding.saveBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show()
        }

    }

    private fun disabledText() {
        binding.nameEditInput.isEnabled = false
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.nameEditInput.windowToken, 0)
    }
}