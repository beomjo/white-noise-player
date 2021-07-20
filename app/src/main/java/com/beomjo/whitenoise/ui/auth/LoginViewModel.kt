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

package com.beomjo.whitenoise.ui.auth

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.compilation.util.Event
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _googleLoginIntent = MutableLiveData<Intent>()
    val googleLoginIntent: LiveData<Intent>
        get() = _googleLoginIntent

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun onClickGoogleLogin() {
        _googleLoginIntent.value = authRepository.getGoogleSinInIntent()
    }

    @FlowPreview
    fun processGoogleLogin(data: Intent) {
        progress.value = Event(true)
        authRepository.getIdTokenFromIntent(data)
            .flatMapConcat { idToken -> authRepository.getGoogleCredential(idToken) }
            .flatMapConcat { credential -> authRepository.loginWithCredential(credential) }
            .onEach {
                _loginSuccess.value = true
                progress.value = Event(false)
            }
            .catch { e ->
                toast.value = Event(e.cause?.message ?: "Fail")
                progress.value = Event(false)
            }
            .launchIn(viewModelScope)
    }
}
