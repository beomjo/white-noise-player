package com.beomjo.whitenoise.repositories.home

import com.beomjo.whitenoise.model.HomeCategory
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : HomeRepository {
    override suspend fun getHomeCategoryList(): List<HomeCategory> {
        return  firestore.collection("home")
            .document("category")
            .collection("categoryIds")
            .get()
            .await()
            .documents
            .map { it["ref"] }
            .map { it as DocumentReference }
            .map { it.get().await() }
            .map { it.toObject(HomeCategory::class.java)!! }.toList()
    }
}