package com.beomjo.whitenoise.di.sound

import com.beomjo.whitenoise.ui.main.sound.SoundListFragment
import dagger.Subcomponent

@Subcomponent
interface SoundListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SoundListComponent
    }

    fun inject(fragment: SoundListFragment)
}