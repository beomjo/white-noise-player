package com.beomjo.whitenoise.repositories.auth

import android.content.Context
import android.content.Intent
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.exceptions.FirebaseAccountNotFoundException
import com.beomjo.whitenoise.exceptions.IdTokenNotFoundException
import com.beomjo.whitenoise.model.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient
) : AuthRepository {

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getGoogleSinInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    override fun getIdTokenFromIntent(intent: Intent): Flow<String?> {
        return flow {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = accountTask.result
            if (accountTask == null || account == null) {
                error(IdTokenNotFoundException())
            }
            emit(account.idToken)
        }
    }

    override fun getGoogleCredential(idToken: String?): Flow<AuthCredential> {
        return flow {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            emit(credential)
        }
    }

    @ExperimentalCoroutinesApi
    override fun loginWithCredential(credential: AuthCredential): Flow<AuthResult> {
        return callbackFlow {
            firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener { result ->
                    result.user?.let { _ -> offer(result) }
                        ?: close(
                            cause = FirebaseAccountNotFoundException(
                                context.getString(
                                    R.string.error_firebase_auth_fail
                                )
                            )
                        )
                }
                .addOnFailureListener(::close)
            awaitClose()
        }
    }

    override fun getUserInfo(): User {
        val user = firebaseAuth.currentUser
        return User(
            nickname = user?.displayName ?: context.getString(R.string.user_anonymous),
            photoUri = user?.photoUrl
        )
    }
}
