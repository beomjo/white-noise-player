package com.beomjo.whitenoise.ui.main.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.track.TrackListRepository
import javax.inject.Inject

class TrackListViewModel @Inject constructor(
    private val trackListRepository: TrackListRepository
) : BaseViewModel() {
    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> get() = _tracks

    fun loadTrackList(documentPath: String) {
        launch { _tracks.value = trackListRepository.getTrackList(documentPath) }
    }
}