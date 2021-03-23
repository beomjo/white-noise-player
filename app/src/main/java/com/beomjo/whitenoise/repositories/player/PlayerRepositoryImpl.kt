package com.beomjo.whitenoise.repositories.player

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : PlayerRepository {
    override suspend fun getTrackDownloadUrl(storagePath: String): Uri {
        return storage.reference
            .child(storagePath)
            .downloadUrl
            .await()
    }
}