package com.beomjo.whitenoise.utilities.ext

import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.beomjo.whitenoise.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback


fun AppCompatActivity.applyExitMaterialTransform() {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
}

fun AppCompatActivity.applyMaterialTransform(rootView: View, transitionName: String) {
    rootView.transitionName = transitionName

    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementEnterTransition = getContentTransform(rootView)
    window.sharedElementReturnTransition = getContentTransform(rootView)
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