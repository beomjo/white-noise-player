package com.beomjo.whitenoise.repositories.home

import com.beomjo.whitenoise.model.Category
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : HomeRepository {
    override suspend fun getHomeCategoryList(): List<Category> {
        return firestore.collection("home")
            .document("category")
            .collection("categoryIds")
            .get()
            .await()
            .documents
            .map { it["ref"] }
            .map { it as DocumentReference }
            .map { it.get().await() }
            .map { it.toObject(Category::class.java)!! }.toList()
    }
}
