package com.beomjo.whitenoise.di

import android.content.Context
import com.beomjo.whitenoise.di.auth.AuthComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [FirebaseModule::class, ViewModelFactoryModule::class, ViewModelModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun authComponent(): AuthComponent.Factory
}
