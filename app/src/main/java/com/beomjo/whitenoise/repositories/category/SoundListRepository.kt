package com.beomjo.whitenoise.repositories.category

import com.beomjo.whitenoise.model.Sound

interface SoundListRepository {

    suspend fun getSounds(documentPath: String): List<Sound>
    
}