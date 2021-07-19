package com.beomjo.whitenoise.utilities.ext

import android.graphics.Color
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform

fun Fragment.applyMaterialTransform(targetView: View, transitionName: String) {
    targetView.transitionName = transitionName
    sharedElementEnterTransition = getMaterialTransitionSet()
    sharedElementReturnTransition = getMaterialTransitionSet()
}

private fun getMaterialTransitionSet(): TransitionSet {
    return TransitionSet().apply {
        addTransition(ChangeImageTransform())
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        addTransition(getContentTransform())
    }
}

private fun getContentTransform(): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        duration = 400
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        scrimColor = Color.TRANSPARENT
    }
}
