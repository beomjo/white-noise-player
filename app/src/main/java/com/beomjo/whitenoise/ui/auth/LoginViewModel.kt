package com.beomjo.whitenoise.ui.auth

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

open class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

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
        authRepository.getIdTokenFromIntent(data)
            .flatMapConcat { idToken -> authRepository.getGoogleCredential(idToken) }
            .flatMapConcat { credential -> authRepository.loginWithCredential(credential) }
            .onEach {
                _loginSuccess.value = true
            }
            .catch { e -> Log.e(this::class.simpleName, e.message.toString()) }
            .launchIn(viewModelScope)
    }
}