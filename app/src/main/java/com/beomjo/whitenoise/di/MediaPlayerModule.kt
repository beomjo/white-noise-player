package com.beomjo.whitenoise.di

import android.media.MediaPlayer
import dagger.Module
import dagger.Provides

@Module
object MediaPlayerModule {

    @Provides
    fun provideMediaPlayer(): MediaPlayer {
        return MediaPlayer()
    }
}