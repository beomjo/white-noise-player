package com.beomjo.whitenoise.di.player

import com.beomjo.whitenoise.ui.player.PlayerActivity
import dagger.Subcomponent

@Subcomponent
interface PlayerComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): PlayerComponent
    }

    fun inject(playerActivity: PlayerActivity)
}