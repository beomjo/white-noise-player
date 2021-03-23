package com.beomjo.whitenoise.model

import android.os.Parcelable
import com.beomjo.whitenoise.utilities.ColorMixer
import com.beomjo.whitenoise.utilities.ext.parseColor
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val title: String = "",
    val desc: String = "",
    val colorPrimary: String = "",
    val storagePath: String = "",
) : Parcelable {

    fun getBackgroundColor(): Int {
        return colorPrimary.parseColor()
    }

    fun getMixedBackgroundColor(): Int {
        return ColorMixer.evaluate(colorPrimary.parseColor(), "#66000000".parseColor())
    }

    fun getIntroduce(): String {
        return "$title - $desc"
    }
}