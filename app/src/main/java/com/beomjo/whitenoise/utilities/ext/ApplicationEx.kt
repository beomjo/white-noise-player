package com.beomjo.whitenoise.utilities.ext

import android.app.Application
import com.beomjo.whitenoise.WhiteNoiseApp
import com.beomjo.whitenoise.di.AppComponent

fun Application.getComponent(): AppComponent {
    return (applicationContext as WhiteNoiseApp).appComponent
}