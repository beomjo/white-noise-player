package com.beomjo.whitenoise.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import com.skydoves.bindables.BindingActivity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int,
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface BaseEntryPoints {
        fun getViewModelFactory(): ViewModelFactory
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected var progressDialog: ProgressDialogFragment? = null

    inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            ViewModelProvider(this, viewModelFactory)
                .get(T::class.javaObjectType)
                .apply { observeViewModel(this) }
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

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            BaseEntryPoints::class.java
        )
        viewModelFactory = entryPoint.getViewModelFactory()
        super.onCreate(savedInstanceState)
        bindingLifeCycleOwner()
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
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