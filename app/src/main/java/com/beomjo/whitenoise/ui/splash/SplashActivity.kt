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

package com.beomjo.whitenoise.ui.splash

import android.content.Intent
import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivitySplashBinding
import com.beomjo.whitenoise.ui.auth.LoginActivity
import com.beomjo.whitenoise.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(
    R.layout.activity_splash,
) {

    private val viewModel: SplashViewModel by getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
        viewModel.init()
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
        LoginActivity.startActivity(this)
    }

    private fun moveToMainActivity() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
