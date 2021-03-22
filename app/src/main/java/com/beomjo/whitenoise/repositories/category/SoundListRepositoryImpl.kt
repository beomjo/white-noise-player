package com.beomjo.whitenoise.repositories.category

import com.beomjo.whitenoise.model.Sound
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SoundListRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SoundListRepository {
    override suspend fun getSounds(documentPath: String): List<Sound> {
        return firestore.collection("category")
            .document(documentPath)
            .collection("list")
            .get()
            .await()
            .documents
            .map { it.toObject<Sound>()!! }.toList()
    }
}