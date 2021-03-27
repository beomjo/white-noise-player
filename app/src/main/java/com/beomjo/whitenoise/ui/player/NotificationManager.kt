package com.beomjo.whitenoise.ui.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager as SystemNotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.session.MediaSession
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.PlayerActions

object NotificationManager {

    private const val CHANNEL_ID: String = "WhiteNoisePlayer-Channel-ID"

    fun createNotification(context: Context): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, PlayerActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val playPendingInt =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                    action = PlayerActions.PLAY.value
                },
                0,
            )

        val pausePendingInt =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                    action = PlayerActions.PLAY.value
                },
                0,
            )

        val mediaSession = MediaSession(context, "White Noise Player")
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.pause_to_play,
                    "",
                    playPendingInt,
                )
            )
            .setOngoing(true)
            .setContentTitle("White Noise Player")
//            .setContentText("My Awesome Band")
            .setTicker("White Noise Player")
            .setContentIntent(pendingIntent)
            .build()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context)
        }

        return notification
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "White Noise Player"
            val descriptionText = "fafafawklfjl"
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as SystemNotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}