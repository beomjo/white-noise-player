package com.beomjo.whitenoise.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sound(
    val title: String = "",
    val subTitle: String = "",
) : Parcelable