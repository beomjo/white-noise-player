package com.beomjo.whitenoise.utilities.ext

import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform


fun getContentTransformWithFragment(): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        duration = 450
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
    }
}

