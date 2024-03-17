package com.brhn.xpnsr.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.brhn.xpnsr.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashImage = findViewById<ImageView>(R.id.splash_image)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)

        fadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                splashImage.startAnimation(fadeOut)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startActivity(Intent(this@SplashActivity, TransactionsActivity::class.java))
                finish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        splashImage.startAnimation(fadeIn)
    }
}