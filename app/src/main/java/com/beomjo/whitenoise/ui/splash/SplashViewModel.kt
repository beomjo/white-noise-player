package com.beomjo.whitenoise.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState>
        get() = _loginState

    protected fun check() {
        if (authRepository.isLoggedIn()) {
            _loginState.value = LoginSuccess
        } else {
            _loginState.value = LoginBefore
        }
    }

    init {
        check()
    }
}

sealed class LoginState
object LoginBefore : LoginState()
object LoginSuccess : LoginState()
