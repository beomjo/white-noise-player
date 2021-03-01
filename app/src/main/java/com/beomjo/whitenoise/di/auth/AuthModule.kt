package com.beomjo.whitenoise.di.auth

import android.content.Context
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.auth.AuthRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides

@Module
class AuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideGoogleSignInOption(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    @Provides
    fun provideGoogleSignInClient(context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, googleSignInClient)
    }
}