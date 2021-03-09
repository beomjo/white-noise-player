package com.beomjo.whitenoise.repositories.home

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : HomeRepository {
    override fun getHomeCategoryList() {
    }
}