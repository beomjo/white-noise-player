package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.auth.AuthRepositoryImpl
import com.beomjo.whitenoise.repositories.home.HomeRepository
import com.beomjo.whitenoise.repositories.home.HomeRepositoryImpl
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import com.beomjo.whitenoise.repositories.player.PlayerRepositoryImpl
import com.beomjo.whitenoise.repositories.track.TrackListRepository
import com.beomjo.whitenoise.repositories.track.TrackListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun provideTrackListRepository(trackListRepository: TrackListRepositoryImpl): TrackListRepository

    @Binds
    abstract fun providePlayerRepository(playerRepository: PlayerRepositoryImpl): PlayerRepository
}
