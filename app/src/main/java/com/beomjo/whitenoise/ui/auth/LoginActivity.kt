package com.beomjo.whitenoise.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityLoginBinding
import com.beomjo.whitenoise.di.auth.AuthComponent
import com.beomjo.whitenoise.ui.main.MainActivity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    R.layout.activity_login,
    LoginViewModel::class
) {

    private val viewModel: LoginViewModel by getViewModel()

    @FlowPreview
    private val requestGoogleLogin: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            activityResult.data?.let(viewModel::processGoogleLogin)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    private fun bindingViewModel() {
        binding {
            vm = viewModel.apply {
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
        fun startActivity(activity: Activity) {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}