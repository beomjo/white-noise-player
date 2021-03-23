package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.beomjo.whitenoise.model.Sound
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    private val _state = MutableLiveData<SoundState>()
    val state: LiveData<SoundState> get() = _state

    private val _sound = MutableLiveData<Sound?>()
    val sound: LiveData<Sound?> get() = _sound

    val hasData: LiveData<Boolean>
        get() = Transformations.map(sound) {
            it != null
        }

    private val _moveToPlayerActivity = MutableLiveData<Sound>()
    val moveToPlayerActivity: LiveData<Sound> get() = _moveToPlayerActivity

    fun setSound(sound: Sound) {
        _sound.value = sound
    }

    fun onPlay() {

    }

    fun onStop() {

    }

    fun onClose() {
        _sound.value = null
    }

    fun onExpand() {
        _moveToPlayerActivity.value = sound.value
    }
}

sealed class SoundState
object SoundPlaying : SoundState()
object SoundPause : SoundState()