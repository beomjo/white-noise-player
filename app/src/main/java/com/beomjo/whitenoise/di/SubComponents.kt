package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.di.auth.AuthComponent
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(subcomponents = [AuthComponent::class])
class SubComponents