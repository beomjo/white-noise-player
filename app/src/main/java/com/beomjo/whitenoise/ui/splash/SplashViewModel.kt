package com.beomjo.whitenoise.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun init() {
        viewModelScope.launch { check() }
    }

    suspend fun check() {
        delay(SPLASH_DELAY_TIME_MILLIS)
        if (authRepository.isLoggedIn()) {
            _loginState.value = LoginSuccess
        } else {
            _loginState.value = LoginBefore
        }
    }

    companion object {
        const val SPLASH_DELAY_TIME_MILLIS = 2000L
    }
}

sealed class LoginState
object LoginBefore : LoginState()
object LoginSuccess : LoginState()
