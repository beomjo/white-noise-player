package com.beomjo.whitenoise.utilities.ext

import android.view.View
import androidx.fragment.app.Fragment
import com.beomjo.whitenoise.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform

fun Fragment.applyMaterialTransform(rootView: View, transitionName: String) {
    rootView.transitionName = transitionName

    sharedElementEnterTransition = getContentTransform(rootView)
    enterTransition = getContentTransform(rootView)
}

private fun getContentTransform(rootView: View): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        addTarget(rootView)
        duration = 450
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        setAllContainerColors(MaterialColors.getColor(rootView, R.attr.colorSurface))
    }
}