package com.beomjo.whitenoise

import android.app.Application
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.common.WhiteNoseConfig
import com.beomjo.whitenoise.di.AppComponent
import com.beomjo.whitenoise.di.DaggerAppComponent

open class WhiteNoiseApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.init(WhiteNoseConfig())
    }
}