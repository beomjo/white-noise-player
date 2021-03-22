package com.beomjo.whitenoise.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 1,
    val name: String = "",
    val iconUrl: String = "",
    val colorPrimary: String = "",
    val colorSecondary: String = "",
    val backgroundAnimationJson: String = "",
    val path: String = "",
) : Parcelable {

    fun getPrimaryColor(): Int {
        return colorPrimary.parseColor()
    }

    fun getSecondaryColor(): Int {
        return colorSecondary.parseColor()
    }

    private fun String.parseColor(): Int {
        return Color.parseColor(this)
    }
}