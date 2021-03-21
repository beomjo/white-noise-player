package com.beomjo.whitenoise.binding

import android.os.Handler
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

object LottieBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["loadJson", "isPlay"], requireAll = false)
    fun bindLottieAnimationFromJson(
        lottieView: LottieAnimationView,
        jsonString: String?,
        isPlay: Boolean = true,
    ) {
        jsonString?.let {
            with(lottieView) {
                setAnimationFromJson(jsonString, null)
                if (isPlay) {
                    Handler().postDelayed({ playAnimation() }, 600)
                }
            }
        }
    }
}