package com.beomjo.whitenoise.ui.player

import android.media.MediaMetadata
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import androidx.media.MediaBrowserServiceCompat
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import kotlinx.coroutines.*
import javax.inject.Inject


class PlayerService : MediaBrowserServiceCompat() {

    @Inject
    lateinit var playerRepository: PlayerRepository

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val mediaPlayer: MediaPlayer = MediaPlayer()
    private var mediaSession: MediaSessionCompat? = null

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            super.onPrepareFromUri(uri, extras)
            LogUtil.d("onPrepareFromUri")
            extras?.getParcelable<Track>(KEY_PREPARE_TRACK)?.let { prepareMediaPlayer(uri!!, it) }
        }

        private fun prepareMediaPlayer(trackDownloadUri: Uri, track: Track) = serviceScope.launch {
            serviceScope.launch {
                mediaPlayer.reset()
                setDataSource(trackDownloadUri)
                mediaPlayer.setOnPreparedListener {
                    mediaPlayer.start()
                    startForegroundService(track)
                }
                mediaPlayer.prepareAsync()
            }
        }

        override fun onPlay() {
            super.onPlay()
            LogUtil.d("onPlay")
            mediaPlayer.start()
        }

        override fun onPause() {
            super.onPause()
            LogUtil.d("onPause")
            mediaPlayer.pause()
        }

        override fun onStop() {
            super.onStop()
            LogUtil.d("onStop")
            mediaPlayer.release()
        }

        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)
            mediaPlayer.isLooping = PlaybackStateCompat.REPEAT_MODE_NONE != repeatMode
        }
    }

    private suspend fun setDataSource(uri: Uri) = withContext(Dispatchers.IO) {
        mediaPlayer.setDataSource(uri.toString())
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.d("onCreate")
        initMediaSession()
    }

    private fun initMediaSession() {
        mediaSession = MediaSessionCompat(this, "White Noise Player").apply {
            setMetadata(
                MediaMetadataCompat.Builder()
                    .putString(MediaMetadata.METADATA_KEY_TITLE, "hello")
                    .putString(MediaMetadata.METADATA_KEY_ARTIST, "world")
                    .build()
            )

            setPlaybackState(
                PlaybackStateCompat.Builder()
                    .setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
                    .build()
            )

            setCallback(callback)

            setSessionToken(sessionToken)
        }
    }


    private fun startForegroundService(track: Track) {
        startForeground(NOTIFICATION_ID, NotificationManager.createNotification(this, track))
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        LogUtil.d("onGetRoot")
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
        super.onDestroy()
        mediaSession?.run {
            isActive = false
            release()
        }
        serviceJob.cancel()
        mediaPlayer.release()
    }

    companion object {
        const val NOTIFICATION_ID = 33
        const val KEY_PREPARE_TRACK = "KEY_PREPARE_TRACK"
    }
}