package com.beomjo.whitenoise.repositories.auth

interface AuthRepository {

    fun isLoggedIn(): Boolean

    fun login()
}