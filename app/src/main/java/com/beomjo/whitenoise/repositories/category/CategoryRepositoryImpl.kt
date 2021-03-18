package com.beomjo.whitenoise.repositories.category

import com.beomjo.whitenoise.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CategoryRepository {
    override suspend fun getCategories(): List<Category> {
        return listOf()
    }
}