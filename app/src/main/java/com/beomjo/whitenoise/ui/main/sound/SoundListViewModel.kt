package com.beomjo.whitenoise.ui.main.sound

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.Sound
import com.beomjo.whitenoise.repositories.category.SoundListRepository
import javax.inject.Inject

class SoundListViewModel @Inject constructor(
    private val soundListRepository: SoundListRepository
) : BaseViewModel() {
    private val _sounds = MutableLiveData<List<Sound>>()
    val sounds: LiveData<List<Sound>> get() = _sounds

    fun loadCategoryList(documentPath: String) {
        launch {
            _sounds.value = soundListRepository.getSounds(documentPath)
        }
    }
}