package com.beomjo.whitenoise.model

import android.os.Parcelable
import android.provider.CalendarContract
import androidx.compose.material.Colors
import com.beomjo.whitenoise.utilities.ColorMixer
import com.beomjo.whitenoise.utilities.ext.parseColor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sound(
    val title: String = "",
    val subTitle: String = "",
    val colorPrimary: String = "",
) : Parcelable {

    fun getBackgroundColor(): Int {
        return ColorMixer.evaluate(colorPrimary.parseColor(), "#66000000".parseColor())
    }
}