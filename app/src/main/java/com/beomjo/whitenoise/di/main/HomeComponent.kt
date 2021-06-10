package com.beomjo.whitenoise.di.main

import com.beomjo.whitenoise.ui.main.home.HomeFragment
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }
}