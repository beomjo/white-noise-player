package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.auth.AuthRepositoryImpl
import com.beomjo.whitenoise.repositories.category.TrackListRepository
import com.beomjo.whitenoise.repositories.category.TrackListRepositoryImpl
import com.beomjo.whitenoise.repositories.home.HomeRepository
import com.beomjo.whitenoise.repositories.home.HomeRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideHomeRepository(homeRepository: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun provideTrackListRepository(trackListRepository: TrackListRepositoryImpl): TrackListRepository
}