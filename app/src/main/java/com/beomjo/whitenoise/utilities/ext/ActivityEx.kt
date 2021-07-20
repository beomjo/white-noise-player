/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    window.decorView.background?.let {
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    }
    window.sharedElementEnterTransition = getContentTransform(rootView)
    window.sharedElementReturnTransition = getContentTransform(rootView)
}

private fun getContentTransform(rootView: View): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        addTarget(rootView)
        duration = 400
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        setAllContainerColors(MaterialColors.getColor(rootView, R.attr.colorSurface))
    }
}
