package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.auth.AuthRepositoryImpl
import com.beomjo.whitenoise.repositories.category.CategoryRepository
import com.beomjo.whitenoise.repositories.category.CategoryRepositoryImpl
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
    abstract fun provideCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository
}