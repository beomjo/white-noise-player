package com.beomjo.whitenoise.ui.player

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.beomjo.compilation.util.LogUtil
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val playerServiceConnection: PlayerServiceConnection
) : BaseObservable() {

    val playerScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableLiveData<TrackState>().apply { value = TrackPause }
    val state: LiveData<TrackState> get() = _state

    val isPlaying: LiveData<Boolean> = Transformations.map(_state) { it is TrackPlaying }

    private val _track = MutableLiveData<Track?>()
    val track: LiveData<Track?> get() = _track

    val hasData: LiveData<Boolean> = Transformations.map(_track) { it != null }

    private val _isLoop = MutableLiveData<Boolean>().apply { value = true }
    val isLoop: LiveData<Boolean> get() = _isLoop

    private val _moveToPlayerActivity = MutableLiveData<Track>()
    val moveToPlayerActivity: LiveData<Track> get() = _moveToPlayerActivity

    private val playbackStateObserver = Observer<PlaybackStateCompat> {
        val playbackState = it ?: EMPTY_PLAYBACK_STATE
        val metadata = playerServiceConnection.nowPlaying.value ?: NOTHING_PLAYING
    }

    fun setTrack(track: Track) {
        _track.value = track
        _state.value = TrackPlaying
        loadTrack(track)
    }

    private fun loadTrack(track: Track) {
        playerServiceConnection.subscribe(
            track.title,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: List<MediaBrowserCompat.MediaItem>
                ) {
                    LogUtil.d("onChildrenLoaded")
                }
            })
//        try {
//            playerScope.launch {
//                val uri = playerRepository.getTrackDownloadUrl(track.storagePath)
//                mediaPlayer.reset()
//                setDataSource(uri)
//                mediaPlayer.setOnPreparedListener { mediaPlayer.start() }
//                mediaPlayer.setOnCompletionListener {
//                    if (isLoop.value == true) {
//                        mediaPlayer.start()
//                    } else {
//                        _state.value = TrackPause
//                    }
//                }
//                mediaPlayer.prepareAsync()
//            }
//        } catch (e: Exception) {
//
//        }
    }

//    private suspend fun setDataSource(uri: Uri) = withContext(Dispatchers.IO) {
//        mediaPlayer.setDataSource(uri.toString())
//    }

    fun onPlayOrPause() {
        if (_state.value is TrackPlaying) {
//            mediaPlayer.pause()
            _state.value = TrackPause
        } else {
//            mediaPlayer.start()
            _state.value = TrackPlaying
        }
    }

    fun onExpand() {
        if (hasData.value == true) {
            _moveToPlayerActivity.value = track.value
        }
    }

    fun onPerformLoop() {
        _isLoop.value = !(isLoop.value ?: true)
    }

    // TODO 어디서 호출해야 할지 정해야함
    fun onCleared() {
        playerScope.cancel()
    }
}

sealed class TrackState
object TrackPlaying : TrackState()
object TrackPause : TrackState()