package com.beomjo.whitenoise.ui.player

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.beomjo.whitenoise.BR
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerManager @Inject constructor() : BaseObservable() {

    @get:Bindable
    var isExpand: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.expand)
        }

    fun onPerformPlay() {
        isExpand = !isExpand
    }
}