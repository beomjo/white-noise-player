package com.beomjo.whitenoise.repositories.auth

import android.content.Intent
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isLoggedIn(): Boolean

    fun getGoogleSinInIntent(): Intent

    fun getIdTokenFromIntent(intent: Intent): Flow<String?>

    fun getGoogleCredential(idToken: String?): Flow<AuthCredential>

    fun loginWithCredential(credential: AuthCredential): Flow<AuthResult>
}