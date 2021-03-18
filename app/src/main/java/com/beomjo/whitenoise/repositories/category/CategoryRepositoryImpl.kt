package com.beomjo.whitenoise.repositories.category

import com.beomjo.whitenoise.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {
    override suspend fun getCategories(documentPath: String): List<Category> {
        return firestore.collection("category")
            .document(documentPath)
            .collection("list")
            .get()
            .await()
            .documents
            .map { it.toObject<Category>()!! }.toList()
    }
}