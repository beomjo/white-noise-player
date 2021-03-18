package com.beomjo.whitenoise.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.compilation.util.Event
import com.skydoves.bindables.BindingViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : BindingViewModel() {
    val toast = MutableLiveData<Event<String>>()

    val progress = MutableLiveData<Event<Boolean>>()

    fun launch(
        block: suspend () -> Unit,
    ): Job {
        return launchBlock(block, null)
    }

    fun launchWithErrorHandle(
        block: suspend () -> Unit,
        handleException: (() -> Unit?)? = null,
    ): Job {
        return launchBlock(block, handleException)
    }

    private fun launchBlock(
        block: suspend () -> Unit,
        handleException: (() -> Unit?)? = null,
    ): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                handleException?.let { handleException() }
                toast.value = Event(e.message ?: "Error")
            }
        }
    }
}