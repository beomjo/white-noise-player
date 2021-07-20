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
