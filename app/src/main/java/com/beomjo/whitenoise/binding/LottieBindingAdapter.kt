package com.beomjo.whitenoise.binding

import android.os.Handler
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object LottieBindingAdapter {
    @JvmStatic
    @BindingAdapter("loadJson")
    fun bindLottieAnimationFromJson(lottieView: LottieAnimationView, jsonString: String?) {
        jsonString?.let {
            with(lottieView) {
                setAnimationFromJson(jsonString, null)
                Handler().postDelayed({ playAnimation() }, 500)
            }
        }
    }
}