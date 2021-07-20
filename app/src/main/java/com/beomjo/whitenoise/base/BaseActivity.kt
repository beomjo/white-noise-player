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

package com.beomjo.whitenoise.base

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import com.skydoves.bindables.BindingActivity

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int,
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    protected var progressDialog: ProgressDialogFragment? = null

    inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            ViewModelProvider(this)
                .get(T::class.java)
                .apply { observeViewModel(this) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLifeCycleOwner()
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
        }
    }

    protected fun setStatusBarColor(color: Int) {
        if (!isFinishing) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    fun observeViewModel(viewModel: BaseViewModel) {
        observeToast(viewModel)
        observeProgress(viewModel)
    }

    private fun observeToast(vm: BaseViewModel) {
        vm.toast.observe(this) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeProgress(vm: BaseViewModel) {
        vm.progress.observe(this) { event ->
            event.getContentIfNotHandled()?.let { isShow ->
                if (isShow) {
                    ProgressDialogFragment.newInstance()
                        .apply { progressDialog = this }
                        .showSafely(supportFragmentManager)
                } else {
                    progressDialog?.dismiss()
                }
            }
        }
    }
}
