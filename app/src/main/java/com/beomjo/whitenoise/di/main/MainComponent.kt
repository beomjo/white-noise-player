package com.beomjo.whitenoise.di.main

import com.beomjo.whitenoise.ui.main.MainActivity
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

}