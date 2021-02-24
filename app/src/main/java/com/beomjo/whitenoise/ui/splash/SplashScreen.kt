package com.beomjo.whitenoise.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}