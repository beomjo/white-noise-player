package com.beomjo.whitenoise.di.track

import com.beomjo.whitenoise.ui.main.track.TrackListFragment
import dagger.Subcomponent

@Subcomponent
interface TrackListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TrackListComponent
    }

    fun inject(fragment: TrackListFragment)
}