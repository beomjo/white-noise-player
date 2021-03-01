package com.beomjo.whitenoise.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.skydoves.bindables.BindingActivity

abstract class BaseActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindingActivity<T>(contentLayoutId), LifecycleOwner {

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLifeCycleOwner()
    }

    private fun bindingLifeCycleOwner() {
        binding {
            lifecycleOwner = this@BaseActivity
        }
    }
}