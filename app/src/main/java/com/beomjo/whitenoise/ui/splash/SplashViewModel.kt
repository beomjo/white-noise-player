package com.beomjo.whitenoise.ui.splash

import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(val authRepository: AuthRepository) : BaseViewModel() {

}