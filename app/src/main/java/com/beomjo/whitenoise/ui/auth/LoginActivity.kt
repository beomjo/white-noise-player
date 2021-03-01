package com.beomjo.whitenoise.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityLoginBinding
import com.beomjo.whitenoise.ui.main.MainActivity
import com.beomjo.whitenoise.utilities.ext.getComponent
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    @Inject
    lateinit var loginViewModel: LoginViewModel

    private val requestGoogleLogin: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.let(loginViewModel::processGoogleLogin)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        bindingViewModel()
    }

    private fun inject() {
        application.getComponent().authComponent().create().inject(this@LoginActivity)
    }

    private fun bindingViewModel() {
        binding {
            vm = loginViewModel.apply {
                googleLoginIntent.observe(this@LoginActivity, requestGoogleLogin::launch)
                loginSuccess.observe(this@LoginActivity) { moveToMainActivity() }
            }
        }
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

    companion object {
        const val GOOGLE_LOGIN_RESULT_CODE = 3333
    }
}