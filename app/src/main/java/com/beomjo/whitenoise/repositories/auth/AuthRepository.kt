package com.beomjo.whitenoise.repositories.auth

import android.content.Intent

interface AuthRepository {

    fun isLoggedIn(): Boolean

    fun getGoogleSinInIntent(): Intent
}