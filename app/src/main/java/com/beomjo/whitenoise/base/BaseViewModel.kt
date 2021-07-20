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
