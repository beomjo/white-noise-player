package com.beomjo.whitenoise.repositories.track

import com.beomjo.whitenoise.model.Track
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class TrackListRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TrackListRepository {
    override suspend fun getTrackList(documentPath: String): List<Track> {
        return firestore.collection("category")
            .document(documentPath)
            .collection("list")
            .get()
            .await()
            .documents
            .map { it.toObject<Track>()!! }.toList()
    }
}
