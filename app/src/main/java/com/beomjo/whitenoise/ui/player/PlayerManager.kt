package com.beomjo.whitenoise.ui.player

import android.content.Intent
import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    private val _state = MutableLiveData<SoundState>()
    val state: LiveData<SoundState> get() = _state

    private val _hasData = MutableLiveData<Boolean>().apply { value = false }
    val hasData: LiveData<Boolean> get() = _hasData

    private val _moveToPlayerActivity = MutableLiveData<Intent>()
    val moveToPlayerActivity: LiveData<Intent> get() = _moveToPlayerActivity


    fun onPlay() {

    }

    fun onStop() {

    }

    fun onClose() {

    }

    fun onExpand() {
    }

//    @get:Bindable
//    var isExpand: Boolean = false
//        set(value) {
//            field = value
//            notifyPropertyChanged(BR.expand)
//        }

}

sealed class SoundState
object SoundPlaying : SoundState()
object SoundPause : SoundState()