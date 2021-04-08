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
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<T : ViewDataBinding>(
    @LayoutRes contentLayoutId: Int,
    private vararg var viewModels: KClass<out BaseViewModel>,
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModelImpl: MutableList<BaseViewModel> = mutableListOf()

    protected var progressDialog: ProgressDialogFragment? = null

    protected inline fun <reified T : BaseViewModel> getViewModel(): Lazy<T> {
        return lazy {
            viewModelImpl.find { it is T }?.let { it as T }
                ?: kotlin.run { throw IllegalStateException("Can't find [${T::class.java.simpleName}] type ViewModel") }
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
        super.onCreate(savedInstanceState)
        inject()
        createViewModels()
        bindingLifeCycleOwner()
        observeViewModel()
    }

    abstract fun inject()

    private fun createViewModels() {
        for (vm in viewModels) {
            viewModelImpl.add(ViewModelProvider(this, viewModelFactory).get(vm.javaObjectType))
        }
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
        }
    }

    private fun observeViewModel() {
        for (vm in viewModelImpl) {
            observeToast(vm)
            observeProgress(vm)
        }
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