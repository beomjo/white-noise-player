package com.beomjo.whitenoise.ui.player

import android.content.Intent
import android.media.MediaMetadata
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.media.MediaBrowserServiceCompat
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.PlayerActions


class PlayerService : MediaBrowserServiceCompat() {

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var mediaSession: MediaSessionCompat? = null
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPlay() {
            super.onPlay()
            LogUtil.d("onPlay")
        }

        override fun onPause() {
            super.onPause()
            LogUtil.d("onPause")
        }

        override fun onStop() {
            super.onStop()
            LogUtil.d("onStop")
        }
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("onCreate")
        mediaSession = MediaSessionCompat(this, "White Noise Player").apply {
            setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, "hello")
                    .putString(MediaMetadata.METADATA_KEY_ARTIST, "world")
                    .build()
            )

            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY
                            or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())

            setCallback(callback)

            setSessionToken(sessionToken)
        }
    }

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

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        LogUtil.d("onGetRoot")
        return BrowserRoot(getString(R.string.app_name), null)
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        return result.sendResult(null)
    }

    companion object {
        const val NOTIFICATION_ID = 33
    }
}