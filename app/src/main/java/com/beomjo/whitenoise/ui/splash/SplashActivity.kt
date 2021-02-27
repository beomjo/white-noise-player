package com.beomjo.whitenoise.ui.splash

import android.content.Intent
import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivitySplashBinding
import com.beomjo.whitenoise.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}