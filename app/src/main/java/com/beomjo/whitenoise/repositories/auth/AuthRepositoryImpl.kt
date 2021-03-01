package com.beomjo.whitenoise.repositories.auth

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun login() {
    }
}