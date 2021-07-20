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

package com.beomjo.whitenoise.binding

import android.os.Handler
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object LottieBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadJson")
    fun bindLottieAnimationFromJson(
        lottieView: LottieAnimationView,
        jsonString: String?,
    ) {
        jsonString?.let {
            with(lottieView) {
                setAnimationFromJson(jsonString, null)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("isPlay")
    fun bindLotteAnimationState(
        lottieView: LottieAnimationView,
        isPlay: Boolean = true,
    ) {
        with(lottieView) {
            if (isPlay) {
                Handler().postDelayed({ playAnimation() }, 600L)
            } else {
                pauseAnimation()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("loadRes")
    fun bindLottieAnimationFromRes(
        lottieView: LottieAnimationView,
        @RawRes res: Int?,
    ) {
        res?.let {
            with(lottieView) {
                setAnimation(res)
            }
        }
    }
}
