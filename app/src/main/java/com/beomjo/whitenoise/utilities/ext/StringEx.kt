package com.beomjo.whitenoise.utilities.ext

import android.graphics.Color

fun String.parseColor(): Int {
    return Color.parseColor(this)
}
