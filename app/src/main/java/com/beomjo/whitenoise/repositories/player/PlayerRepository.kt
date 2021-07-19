package com.beomjo.whitenoise.repositories.player

import android.net.Uri

interface PlayerRepository {
    suspend fun getTrackDownloadUrl(storagePath: String): Uri
}
