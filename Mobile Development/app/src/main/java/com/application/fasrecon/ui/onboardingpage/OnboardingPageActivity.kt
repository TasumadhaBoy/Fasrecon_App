package com.application.fasrecon.ui.onboardingpage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivityOnboardingPageBinding

class OnboardingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginOnboardingPage.setOnClickListener {

        }

        binding.btnSignupOnboardingPage.setOnClickListener {

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}