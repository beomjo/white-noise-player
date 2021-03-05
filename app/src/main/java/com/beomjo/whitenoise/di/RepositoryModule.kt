package com.beomjo.whitenoise.di

import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.auth.AuthRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
object RepositoryModule {

    @Provides
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, googleSignInClient)
    }
}