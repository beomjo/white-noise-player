package com.beomjo.whitenoise.di.track

import com.beomjo.whitenoise.ui.main.track.TrackListFragment
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface TrackListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TrackListComponent
    }
}