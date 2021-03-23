package com.beomjo.whitenoise.model

import android.os.Parcelable
import com.beomjo.whitenoise.utilities.ColorMixer
import com.beomjo.whitenoise.utilities.ext.parseColor
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sound(
    val title: String = "",
    val subTitle: String = "",
    val colorPrimary: String = "",
) : Parcelable {

    fun getBackgroundColor(): Int {
        return colorPrimary.parseColor()
    }

    fun getMixedBackgroundColor(): Int {
        return ColorMixer.evaluate(colorPrimary.parseColor(), "#66000000".parseColor())
    }
}