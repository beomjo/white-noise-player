package com.beomjo.whitenoise.di

import android.content.ComponentName
import android.content.Context
import android.media.MediaPlayer
import com.beomjo.whitenoise.ui.player.PlayerService
import com.beomjo.whitenoise.ui.player.PlayerServiceConnection
import dagger.Module
import dagger.Provides

@Module
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Provides
    fun providePlayerServiceConnection(context: Context): PlayerServiceConnection {
        return PlayerServiceConnection(context, ComponentName(context, PlayerService::class.java))
    }
}