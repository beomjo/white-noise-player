package com.beomjo.whitenoise.ui.player

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.beomjo.whitenoise.model.PlayerActions

class PlayerService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            PlayerActions.START_FOREGROUND.value -> startForegroundService()
            PlayerActions.STOP_FOREGROUND.value -> stopForegroundService()
            PlayerActions.PLAY.value -> doSomething()
            PlayerActions.PAUSE.value -> doSomething()
            else -> {
                //do Nothing
            }
        }
        return START_STICKY
    }

    private fun startForegroundService() {
        startForeground(NOTIFICATION_ID, NotificationManager.createNotification(this))
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    private fun doSomething() {}


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val NOTIFICATION_ID = 33
    }
}