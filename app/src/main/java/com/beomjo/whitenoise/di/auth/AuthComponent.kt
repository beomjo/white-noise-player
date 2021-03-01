package com.beomjo.whitenoise.di.auth

import com.beomjo.whitenoise.ui.auth.LoginActivity
import com.beomjo.whitenoise.ui.splash.SplashActivity
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(activity: SplashActivity)

    fun inject(activity: LoginActivity)

}