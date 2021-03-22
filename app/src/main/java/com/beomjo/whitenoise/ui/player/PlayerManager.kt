package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.lifecycle.*
import com.beomjo.whitenoise.model.Sound
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    private val playerScope = CoroutineScope(Dispatchers.Default)

    private val _state = MutableLiveData<SoundState>()
    val state: LiveData<SoundState> get() = _state

    private val _sound = MutableLiveData<Sound?>()
    val sound: LiveData<Sound?> get() = _sound

    private val _hasData = MutableLiveData<Boolean>().apply { value = false }
    val hasData: LiveData<Boolean> get() = _hasData

    private val _moveToPlayerActivity = MutableLiveData<Sound>()
    val moveToPlayerActivity: LiveData<Sound> get() = _moveToPlayerActivity

    fun setSound(sound: Sound) {
        playerScope.launch { delayWith { _hasData.postValue(true) } }
        _sound.value = sound
    }

    fun onPlay() {

    }

    fun onStop() {

    }

    fun onClose() {
        _sound.value = null
        _hasData.value = false
    }

    fun onExpand() {
        _moveToPlayerActivity.value = sound.value
    }

    private suspend fun <T> delayWith(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            delay(450L)
            block()
        }
    }
}

sealed class SoundState
object SoundPlaying : SoundState()
object SoundPause : SoundState()