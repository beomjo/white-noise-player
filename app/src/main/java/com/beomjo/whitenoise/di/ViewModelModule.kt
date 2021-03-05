package com.beomjo.whitenoise.di

import androidx.lifecycle.ViewModel
import com.beomjo.whitenoise.ui.auth.LoginViewModel
import com.beomjo.whitenoise.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun provideSplashViewModel(splashViewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun provideLoginViewModel(mainViewModel: LoginViewModel): ViewModel
}