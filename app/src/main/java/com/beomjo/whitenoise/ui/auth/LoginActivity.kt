/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beomjo.whitenoise.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityLoginBinding
import com.beomjo.whitenoise.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    R.layout.activity_login,
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
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}
