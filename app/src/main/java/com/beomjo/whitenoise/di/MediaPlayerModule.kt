package com.beomjo.whitenoise.di

import android.content.ComponentName
import android.content.Context
import android.media.MediaPlayer
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.ui.player.PlayerService
import com.beomjo.whitenoise.ui.player.PlayerServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }

    @Singleton
    @Provides
    fun providePlayerManager(
        repository: PlayerRepository,
        connection: PlayerServiceConnection
    ): PlayerManager {
        return PlayerManager(repository, connection)
    }

    @Provides
    fun providePlayerServiceConnection(@ApplicationContext context: Context): PlayerServiceConnection {
        return PlayerServiceConnection(context, ComponentName(context, PlayerService::class.java))
    }
}