package com.beomjo.whitenoise.binding

import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.beomjo.whitenoise.R

object PlayerBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["background", "hasData"])
    fun bindBackgroundWithDefaultColor(
        view: View,
        @ColorInt color: Int,
        hasData: Boolean
    ) {
        if (hasData ) {
            view.setBackgroundColor(color)
        } else {
            view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.gray_02))
        }
    }
}