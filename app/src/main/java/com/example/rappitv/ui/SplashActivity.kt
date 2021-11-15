package com.example.rappitv.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.rappitv.R
import com.example.rappitv.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashBinding
    private lateinit var animation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash_screen)
        mBinding.ivSplashLogoRappi.startAnimation(animation)


        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
    }
}