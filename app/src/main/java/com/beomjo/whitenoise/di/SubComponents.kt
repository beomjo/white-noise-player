package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.di.auth.AuthComponent
import dagger.Module

@Module(subcomponents = [AuthComponent::class])
class SubComponents