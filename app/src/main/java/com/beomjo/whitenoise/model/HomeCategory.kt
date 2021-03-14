package com.beomjo.whitenoise.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HomeCategory(
    val id: Int = 1,
    val name: String = "",
    val iconUrl: String = "",
    val colorPrimary: String = "",
    val colorSecondary: String = "",
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