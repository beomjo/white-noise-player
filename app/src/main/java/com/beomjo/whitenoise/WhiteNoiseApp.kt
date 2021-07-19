package com.beomjo.whitenoise

import android.app.Application
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.common.WhiteNoseConfig
import com.beomjo.whitenoise.ui.player.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class WhiteNoiseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.init(WhiteNoseConfig())
        NotificationManager.createNotificationChannel(this)
    }
}
