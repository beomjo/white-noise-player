package com.beomjo.whitenoise.model

import android.net.Uri

data class User(
    val nickname: String,
    val photoUri: Uri?,
) {
    fun getDisplayNickname(): String {
        return "Hello, $nickname!"
    }
}
