package com.beomjo.whitenoise.ui.player

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.player.PlayerRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor(
    private val playerRepository: PlayerRepository
) : BaseObservable() {

    val playerScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableLiveData<TrackState>().apply { value = TrackPause }
    val state: LiveData<TrackState> get() = _state

    val isPlaying: LiveData<Boolean> = Transformations.map(_state) { it is TrackPlaying }

    private val _track = MutableLiveData<Track?>()
    val track: LiveData<Track?> get() = _track

    private val _trackSoundUri = MutableLiveData<Uri>()
    val trackSoundUri: LiveData<Uri> get() = _trackSoundUri

    val hasData: LiveData<Boolean> = Transformations.map(_track) { it != null }

    private val _isLoop = MutableLiveData<Boolean>().apply { value = true }
    val isLoop: LiveData<Boolean> get() = _isLoop

    private val _moveToPlayerActivity = MutableLiveData<Track>()
    val moveToPlayerActivity: LiveData<Track> get() = _moveToPlayerActivity

    fun setTrack(track: Track) {
        _track.value = track
        _state.value = TrackPlaying
        loadTrack(track)
    }

    private fun loadTrack(track: Track) {
        try {
            playerScope.launch {
                val uri = playerRepository.getTrackDownloadUrl(track.storagePath)
                _trackSoundUri.value = uri
            }
        } catch (e: Exception) {

        }
    }

    fun onPlayOrPause() {
        _state.value = if (_state.value is TrackPlaying) TrackPause else TrackPlaying
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