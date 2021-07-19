package com.beomjo.whitenoise.model

import android.os.Parcelable
import com.beomjo.whitenoise.utilities.ext.parseColor
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int = 1,
    val name: String = "",
    val iconUrl: String = "",
    val colorPrimary: String = "",
    val backgroundAnimationJson: String = "",
    val path: String = "",
) : Parcelable {

    fun getPrimaryColor(): Int {
        return colorPrimary.parseColor()
    }
}
