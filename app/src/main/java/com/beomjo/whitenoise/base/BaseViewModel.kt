package com.beomjo.whitenoise.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skydoves.bindables.BindingViewModel

abstract class BaseViewModel : BindingViewModel() {
    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String>
        get() = _toast
}