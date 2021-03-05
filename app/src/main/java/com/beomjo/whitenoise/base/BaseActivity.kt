package com.beomjo.whitenoise.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.beomjo.whitenoise.factory.ViewModelFactory
import com.beomjo.whitenoise.ui.splash.SplashViewModel
import com.skydoves.bindables.BindingActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding, VM : BaseViewModel> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: VM

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        createViewModel()
        bindingLifeCycleOwner()
    }

    private fun createViewModel() {
        val clazz = ((javaClass.genericSuperclass as ParameterizedType?)
            ?.actualTypeArguments
            ?.get(1) as Class<VM>)
        viewModel = ViewModelProvider(this, viewModelFactory).get(clazz)
    }

    abstract fun inject()

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
        }
    }
}