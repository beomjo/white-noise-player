package com.beomjo.whitenoise.base

import androidx.lifecycle.MutableLiveData
import com.beomjo.compilation.util.Event
import com.skydoves.bindables.BindingViewModel

abstract class BaseViewModel : BindingViewModel() {
    val toast = MutableLiveData<Event<String>>()

    val progress = MutableLiveData<Event<Boolean>>()
}