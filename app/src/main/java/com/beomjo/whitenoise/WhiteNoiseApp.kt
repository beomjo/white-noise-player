package com.beomjo.whitenoise

import android.app.Application
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.common.WhiteNoseConfig
import com.beomjo.whitenoise.di.AppComponent
import com.beomjo.whitenoise.di.DaggerAppComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class WhiteNoiseApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.init(WhiteNoseConfig())
        com.beomjo.whitenoise.ui.player.NotificationManager.createNotificationChannel(this)
    }
}