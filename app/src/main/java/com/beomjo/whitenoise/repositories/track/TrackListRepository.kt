package com.beomjo.whitenoise.repositories.track

import com.beomjo.whitenoise.model.Track

interface TrackListRepository {

    suspend fun getTrackList(documentPath: String): List<Track>
    
}