package com.beomjo.whitenoise.ui.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager as SystemNotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.os.Build
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.PlayerAction
import com.beomjo.whitenoise.model.Track

object NotificationManager {

    private const val CHANNEL_ID: String = "WhiteNoisePlayer-Channel-ID"

    fun createPlayNotification(context: Context, track: Track): Notification {
        val playPendingInt =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                    action = PlayerAction.PLAY.value
                },
                0,
            )
        val action = NotificationCompat.Action(
            R.drawable.ic_play_arrow_white,
            "",
            playPendingInt,
        )
        return createNotification(context, track, action)
    }

    fun createPauseNotification(context: Context, track: Track): Notification {
        val pausePendingInt =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                    action = PlayerAction.PAUSE.value
                },
                0,
            )
        val action = NotificationCompat.Action(
            R.drawable.ic_pause_white,
            "",
            pausePendingInt,
        )
        return createNotification(context, track, action)
    }

    private fun createNotification(
        context: Context,
        track: Track,
        action: NotificationCompat.Action
    ): Notification {
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.KEY_PLAYER_TRACK, track)
                putExtra(PlayerActivity.KEY_BOTTOM_PLAYER_CLICK, true)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopPendingIntent =
            PendingIntent.getService(
                context, 0,
                Intent(context, PlayerService::class.java).apply {
                    this.action = PlayerAction.STOP.value
                },
                0,
            )
        val stopAction = NotificationCompat.Action(
            R.drawable.ic_close_white,
            "",
            stopPendingIntent,
        )

        val appName = context.getString(R.string.app_name)
        val mediaSession = MediaSessionCompat(context, appName).apply {
            setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, track.title)
                    .putString(MediaMetadata.METADATA_KEY_ARTIST, track.desc)
                    .build()
            )
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(action)
            .addAction(stopAction)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1)
                    .setMediaSession(mediaSession.sessionToken)
            )
            .setOngoing(true)
            .setContentTitle(context.getString(R.string.app_name))
            .setTicker(appName)
            .setContentIntent(pendingIntent)
            .build()
    }

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appName = context.getString(R.string.app_name)
            val importance = android.app.NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(CHANNEL_ID, appName, importance)
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as SystemNotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
