package com.beomjo.whitenoise.binding

import android.os.Handler
import androidx.annotation.RawRes
import androidx.databinding.BindingAdapter
import androidx.transition.Transition
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