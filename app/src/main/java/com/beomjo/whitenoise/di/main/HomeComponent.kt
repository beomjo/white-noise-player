package com.beomjo.whitenoise.di.main

import com.beomjo.whitenoise.ui.main.home.HomeFragment
import dagger.Subcomponent

@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(fragment: HomeFragment)
}