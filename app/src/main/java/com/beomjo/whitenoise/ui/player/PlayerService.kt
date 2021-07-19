package com.beomjo.whitenoise.ui.player

import android.app.Notification
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import androidx.media.MediaBrowserServiceCompat
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.PlayerAction
import com.beomjo.whitenoise.model.Track
import kotlinx.coroutines.*

class PlayerService : MediaBrowserServiceCompat() {

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var mediaSession: MediaSessionCompat? = null
    private var track: Track? = null

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            extras?.getParcelable<Track>(KEY_PREPARE_TRACK)?.let { prepareMediaPlayer(uri!!, it) }
        }

        private fun prepareMediaPlayer(trackDownloadUri: Uri, track: Track) = serviceScope.launch {
            serviceScope.launch {
                this@PlayerService.track = track
                mediaPlayer.reset()
                setDataSource(trackDownloadUri)
                mediaPlayer.setOnPreparedListener {
                    mediaPlayer.start()
                    updatePlaybackState(
                        PlaybackStateCompat.STATE_PLAYING,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                    startForegroundService(createNotification(PlayerAction.PAUSE, track))
                }
                mediaPlayer.setOnCompletionListener {
                    if (isLooping()) {
                        this@PlayerService.onPlay()
                    } else {
                        this@PlayerService.onPause()
                    }
                }
                mediaPlayer.prepareAsync()
            }
        }

        private suspend fun setDataSource(uri: Uri) = withContext(Dispatchers.IO) {
            mediaPlayer.setDataSource(uri.toString())
        }

        private fun isLooping(): Boolean =
            PlaybackStateCompat.REPEAT_MODE_ONE == mediaSession?.controller?.repeatMode

        override fun onPlay() {
            this@PlayerService.onPlay()
        }

        override fun onPause() {
            this@PlayerService.onPause()
        }

        override fun onStop() {
            this@PlayerService.onStop()
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)
            mediaSession?.setRepeatMode(repeatMode)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(this, baseContext.getString(R.string.app_name)).apply {
            setCallback(callback)
            setSessionToken(sessionToken)
            isActive = true
        }
    }

    private fun onPlay() {
        mediaPlayer.start()
        updatePlaybackState(
            PlaybackStateCompat.STATE_PLAYING,
            PlaybackStateCompat.ACTION_PLAY_PAUSE
        )
        track?.let { startForegroundService(createNotification(PlayerAction.PAUSE, it)) }
    }

    private fun onPause() {
        mediaPlayer.pause()
        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.ACTION_PLAY_PAUSE)
        track?.let { startForegroundService(createNotification(PlayerAction.PLAY, it)) }
    }

    private fun onStop() {
        mediaPlayer.pause()
        stopForegroundService()
        updatePlaybackState(PlaybackStateCompat.STATE_STOPPED, PlaybackStateCompat.ACTION_STOP)
    }

    private fun updatePlaybackState(state: Int, action: Long) {
        mediaSession?.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setState(state, mediaPlayer.currentPosition.toLong(), 0.0f)
                .setActions(action).build()
        )
    }

    private fun createNotification(playerAction: PlayerAction, track: Track): Notification {
        return when (playerAction) {
            PlayerAction.PLAY -> NotificationManager.createPlayNotification(
                this@PlayerService,
                track
            )
            PlayerAction.PAUSE -> NotificationManager.createPauseNotification(
                this@PlayerService,
                track
            )
            PlayerAction.STOP -> throw IllegalStateException()
        }
    }

    private fun startForegroundService(notification: Notification) {
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            PlayerAction.PLAY.value -> onPlay()
            PlayerAction.PAUSE.value -> onPause()
            PlayerAction.STOP.value -> onStop()
        }
        return START_STICKY
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return if (TextUtils.equals(clientPackageName, packageName)) {
            BrowserRoot(getString(R.string.app_name), null)
        } else null
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        return result.sendResult(null)
    }

    override fun onDestroy() {
        onStop()
        track = null
        mediaPlayer.release()
        mediaSession?.run {
            isActive = false
            release()
        }
        serviceJob.cancel()
        mediaPlayer.release()
        super.onDestroy()
    }

    companion object {
        const val NOTIFICATION_ID = 33
        const val KEY_PREPARE_TRACK = "KEY_PREPARE_TRACK"
    }
}
