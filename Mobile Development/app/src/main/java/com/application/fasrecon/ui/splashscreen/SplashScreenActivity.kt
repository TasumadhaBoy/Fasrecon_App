package com.application.fasrecon.ui.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.fasrecon.R
import com.application.fasrecon.databinding.ActivitySplashScreenBinding
import com.application.fasrecon.ui.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2200)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showAnimation() {
        val background1ScaleX = ObjectAnimator.ofFloat(binding.logoBackground1, View.SCALE_X, 1f, 1.25f).apply {
            duration = 1000
            startDelay = 200
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 2
        }
        val background1ScaleY = ObjectAnimator.ofFloat(binding.logoBackground1, View.SCALE_Y, 1f, 1.25f).apply {
            duration = 1000
            startDelay = 200
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 2
        }

        val background2ScaleX = ObjectAnimator.ofFloat(binding.logoBackground2, View.SCALE_X, 1f, 1.25f).apply {
            duration = 1000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 2
        }
        val background2ScaleY = ObjectAnimator.ofFloat(binding.logoBackground2, View.SCALE_Y, 1f, 1.25f).apply {
            duration = 1000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 2
        }

        AnimatorSet().apply {
            playTogether(background1ScaleX, background1ScaleY, background2ScaleX, background2ScaleY)
            start()
        }
    }
}