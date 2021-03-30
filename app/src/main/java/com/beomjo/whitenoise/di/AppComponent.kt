package com.beomjo.whitenoise.di

import android.content.Context
import android.media.MediaPlayer
import com.beomjo.whitenoise.di.auth.AuthComponent
import com.beomjo.whitenoise.di.common.CommonComponent
import com.beomjo.whitenoise.di.main.HomeComponent
import com.beomjo.whitenoise.di.main.MainComponent
import com.beomjo.whitenoise.di.player.PlayerComponent
import com.beomjo.whitenoise.di.track.TrackListComponent
import com.beomjo.whitenoise.ui.player.PlayerManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FirebaseModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        MediaPlayerModule::class,
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun commonComponent(): CommonComponent.Factory

    fun authComponent(): AuthComponent.Factory

    fun mainComponent(): MainComponent.Factory

    fun homeComponent(): HomeComponent.Factory

    fun trackListComponent(): TrackListComponent.Factory

    fun playerComponent(): PlayerComponent.Factory

    fun playerManager(): PlayerManager

    fun mediaPlayer(): MediaPlayer
}
