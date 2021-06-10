package com.beomjo.whitenoise.di.player

import com.beomjo.whitenoise.ui.player.PlayerActivity
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface PlayerComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PlayerComponent
    }
}