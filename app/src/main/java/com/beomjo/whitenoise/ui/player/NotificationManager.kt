package com.beomjo.whitenoise.ui.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.Track
import android.app.NotificationManager as SystemNotificationManager


object NotificationManager {

    private const val CHANNEL_ID: String = "WhiteNoisePlayer-Channel-ID"

    fun createNotification(context: Context, track: Track): Notification {
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
                },
                0,
            )

        val pausePendingInt =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                },
                0,
            )

        val mediaSession = MediaSessionCompat(context, "White Noise Player").apply {
            setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, track.title)
                    .putString(MediaMetadata.METADATA_KEY_ARTIST, track.desc)
                    .build()
            )
        }
        val icon =
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher_background)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
//            .setLargeIcon(icon)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.ic_arrow_back_white,
                    "",
                    playPendingInt,
                )
            )
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0)
                    .setShowCancelButton(true)
                    .setMediaSession(mediaSession.sessionToken)
                    .setCancelButtonIntent(
                        MediaButtonReceiver.buildMediaButtonPendingIntent(
                            context, PlaybackStateCompat.ACTION_STOP
                        )
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
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as SystemNotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}