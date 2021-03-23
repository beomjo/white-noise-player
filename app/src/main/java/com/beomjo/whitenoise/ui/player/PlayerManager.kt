package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.beomjo.whitenoise.model.Sound
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    private val _state = MutableLiveData<SoundState>().apply { value = SoundPause }
    val state: LiveData<SoundState> get() = _state

    val isPlaying: LiveData<Boolean> = Transformations.map(_state) { it is SoundPlaying }

    private val _sound = MutableLiveData<Sound?>()
    val sound: LiveData<Sound?> get() = _sound

    val hasData: LiveData<Boolean> = Transformations.map(_sound) { it != null }

    private val _moveToPlayerActivity = MutableLiveData<Sound>()
    val moveToPlayerActivity: LiveData<Sound> get() = _moveToPlayerActivity

    fun setSound(sound: Sound) {
        _sound.value = sound
    }

    fun onPlayOrPause() {
        _state.value = if (_state.value is SoundPlaying) SoundPause else SoundPlaying
    }

    fun onExpand() {
        if (hasData.value == true) {
            _moveToPlayerActivity.value = sound.value
        }
    }
}

sealed class SoundState
object SoundPlaying : SoundState()
object SoundPause : SoundState()