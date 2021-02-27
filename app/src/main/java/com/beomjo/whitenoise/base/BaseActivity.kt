package com.beomjo.whitenoise.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.beomjo.compilation.binding.BindActivity

abstract class BaseActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val contentLayoutId: Int
) : BindActivity<T>(contentLayoutId)