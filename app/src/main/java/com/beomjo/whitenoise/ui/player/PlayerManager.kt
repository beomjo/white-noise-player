package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beomjo.whitenoise.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    private val _state = MutableLiveData<TrackState>().apply { value = TrackPause }
    val state: LiveData<TrackState> get() = _state

    val isPlaying: LiveData<Boolean> = Transformations.map(_state) { it is TrackPlaying }

    private val _track = MutableLiveData<Track?>()
    val track: LiveData<Track?> get() = _track

    val hasData: LiveData<Boolean> = Transformations.map(_track) { it != null }

    private val _moveToPlayerActivity = MutableLiveData<Track>()
    val moveToPlayerActivity: LiveData<Track> get() = _moveToPlayerActivity

    fun setTrack(track: Track) {
        _track.value = track
        _state.value = TrackPlaying
    }

    fun onPlayOrPause() {
        _state.value = if (_state.value is TrackPlaying) TrackPause else TrackPlaying
    }

    fun onExpand() {
        if (hasData.value == true) {
            _moveToPlayerActivity.value = track.value
        }
    }
}

sealed class TrackState
object TrackPlaying : TrackState()
object TrackPause : TrackState()