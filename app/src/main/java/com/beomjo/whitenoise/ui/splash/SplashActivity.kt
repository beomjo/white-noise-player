package com.beomjo.whitenoise.ui.splash

import android.content.Intent
import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivitySplashBinding
import com.beomjo.whitenoise.ui.auth.LoginActivity
import com.beomjo.whitenoise.ui.auth.LoginViewModel
import com.beomjo.whitenoise.ui.main.MainActivity
import com.beomjo.whitenoise.utilities.ext.getComponent

class SplashActivity : BaseActivity<ActivitySplashBinding>(
    R.layout.activity_splash,
    SplashViewModel::class,
    LoginViewModel::class,
) {

    private val viewModel: SplashViewModel by getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
        viewModel.check()
    }

    override fun inject() {
        application.getComponent().authComponent().create().inject(this)
    }

    private fun bindingViewModel() {
        binding {
            vm = viewModel
            vm?.loginState?.observe(this@SplashActivity) { loginState ->
                when (loginState) {
                    is LoginBefore -> moveToLoginActivity()
                    is LoginSuccess -> moveToMainActivity()
                }
            }
        }
    }

    private fun moveToLoginActivity() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}