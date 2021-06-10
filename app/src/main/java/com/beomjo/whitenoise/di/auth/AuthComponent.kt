package com.beomjo.whitenoise.di.auth

import com.beomjo.whitenoise.ui.auth.LoginActivity
import com.beomjo.whitenoise.ui.splash.SplashActivity
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }
}