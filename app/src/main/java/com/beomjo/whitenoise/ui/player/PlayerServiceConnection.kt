package com.beomjo.whitenoise.ui.player

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerServiceConnection @Inject constructor(
    context: Context,
    serviceComponent: ComponentName
) {

    val isConnected = MutableLiveData<Boolean>()
        .apply { postValue(false) }

    val playbackState = MutableLiveData<PlaybackStateCompat>()
        .apply { postValue(EMPTY_PLAYBACK_STATE) }

    val isLoop = MutableLiveData<Boolean>().apply { postValue(true) }

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)

    private val mediaBrowser = MediaBrowserCompat(
        context,
        serviceComponent,
        mediaBrowserConnectionCallback, null
    ).apply { connect() }

    private lateinit var mediaController: MediaControllerCompat

    fun subscribe() {
        mediaBrowser.subscribe("ff", object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String, children: List<MediaBrowserCompat.MediaItem>
            ) {
            }
        })
    }

    fun unsubscribe() {
        mediaBrowser.unsubscribe("ff")
    }

    fun prepareAndPlay(trackDownloadUri: Uri, track: Track) {
        if (mediaBrowser.isConnected) {
            val extra = Bundle().apply {
                putParcelable(PlayerService.KEY_PREPARE_TRACK, track)
            }
            mediaController.transportControls.prepareFromUri(trackDownloadUri, extra)
            setLoop(isLoop.value ?: true)
        }
    }

    fun pause() {
        if (mediaBrowser.isConnected) {
            mediaController.transportControls.pause()
        }
    }

    fun play() {
        if (mediaBrowser.isConnected) {
            mediaController.transportControls.play()
        }
    }

    fun setLoop(value: Boolean) {
        val repeatMode = if (value)
            PlaybackStateCompat.REPEAT_MODE_ONE
        else
            PlaybackStateCompat.REPEAT_MODE_NONE
        mediaController.transportControls.setRepeatMode(repeatMode)
    }

    private inner class MediaBrowserConnectionCallback(private val context: Context) :
        MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            super.onConnected()
            mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken).apply {
                registerCallback(MediaControllerCallback())
            }
            isConnected.postValue(true)
        }

        override fun onConnectionSuspended() {
            super.onConnectionSuspended()
            isConnected.postValue(false)
        }

        override fun onConnectionFailed() {
            super.onConnectionFailed()
            isConnected.postValue(false)
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            super.onPlaybackStateChanged(state)
            playbackState.postValue(state ?: EMPTY_PLAYBACK_STATE)
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            isLoop.postValue(repeatMode == PlaybackStateCompat.REPEAT_MODE_ONE)
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }
}

@Suppress("PropertyName")
val EMPTY_PLAYBACK_STATE: PlaybackStateCompat = PlaybackStateCompat.Builder()
    .setState(PlaybackStateCompat.STATE_NONE, 0, 0f)
    .build()