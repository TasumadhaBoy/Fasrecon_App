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
import com.application.fasrecon.ui.onboardingpage.OnboardingPageActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, OnboardingPageActivity::class.java))
            finish()
        }, 2200)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    private fun showAnimation() {
        val background1ScaleX = animationScale(binding.logoBackground1, "SCALE_X", 200)
        val background1ScaleY = animationScale(binding.logoBackground1, "SCALE_Y",200)

        val background2ScaleX = animationScale(binding.logoBackground2, "SCALE_X")
        val background2ScaleY = animationScale(binding.logoBackground2, "SCALE_Y")

        val appNameAlpha = ObjectAnimator.ofFloat(binding.appText, View.ALPHA, 1f).setDuration(1000)
        val appNameScaleX =
            ObjectAnimator.ofFloat(binding.appText, View.SCALE_X, 0.25f, 1f).setDuration(1000)
        val appNameScaleY =
            ObjectAnimator.ofFloat(binding.appText, View.SCALE_Y, 0.25f, 1f).setDuration(1000)

        AnimatorSet().apply {
            playTogether(
                background1ScaleX,
                background1ScaleY,
                background2ScaleX,
                background2ScaleY,
                appNameAlpha,
                appNameScaleX,
                appNameScaleY
            )
            start()
        }
    }

    private fun animationScale(
        view: View,
        type: String,
        mStartDelay: Long = 0
    ): ObjectAnimator {
        var typeView = View.SCALE_X
        if (type == "SCALE_Y") {
            typeView = View.SCALE_Y
        }

        return ObjectAnimator.ofFloat(view, typeView, 1f, 1.25f).apply {
            duration = 1000
            startDelay = mStartDelay
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = 1
        }
    }
}