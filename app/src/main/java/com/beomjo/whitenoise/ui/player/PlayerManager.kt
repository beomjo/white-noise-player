package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import com.beomjo.whitenoise.utilities.ext.isPlay
import kotlinx.coroutines.*
import javax.inject.Inject

class PlayerManager @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val playerServiceConnection: PlayerServiceConnection
) : BaseObservable() {

    val playerScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _isPlaying: LiveData<Boolean> =
        Transformations.map(playerServiceConnection.playbackState) { it.isPlay }
    val isPlaying: LiveData<Boolean> get() = _isPlaying

    private val _track = MutableLiveData<Track?>()
    val track: LiveData<Track?> get() = _track

    private val _hasData: LiveData<Boolean> = Transformations.map(_track) { it != null }
    val hasData: LiveData<Boolean> get() = _hasData

    private val _isLoop: LiveData<Boolean> =
        Transformations.map(playerServiceConnection.isLoop) { it }
    val isLoop: LiveData<Boolean> get() = _isLoop

    private val _moveToPlayerActivity = MutableLiveData<Track>()
    val moveToPlayerActivity: LiveData<Track> get() = _moveToPlayerActivity

    fun init() {
        playerServiceConnection.subscribe()
    }

    fun setTrack(track: Track) {
        _track.value = track
        loadTrack(track)
    }

    private fun loadTrack(track: Track) {
        try {
            playerScope.launch {
                val uri = playerRepository.getTrackDownloadUrl(track.storagePath)
                playerServiceConnection.prepareAndPlay(uri, track)
            }
        } catch (e: Exception) {
            // Do Nothing
        }
    }

    fun onPlayOrPause() {
        if (isPlaying.value == true) {
            playerServiceConnection.pause()
        } else {
            playerServiceConnection.play()
        }
    }

    fun onExpand() {
        if (hasData.value == true) {
            _moveToPlayerActivity.value = track.value
        }
    }

    fun onPerformLoop() {
        playerServiceConnection.setLoop(!(isLoop.value ?: true))
    }

    // TODO 어디서 호출해야 할지 정해야함
    fun onCleared() {
        playerServiceConnection.unsubscribe()
        playerScope.cancel()
    }
}
