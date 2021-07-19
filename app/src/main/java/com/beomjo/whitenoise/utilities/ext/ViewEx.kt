package com.beomjo.whitenoise.utilities.ext

import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

fun <T : View> T.setOnClickAnimation(function: (view: T) -> Unit): T {
    this.setOnClickListener {
        val toggleDuration = 80
        val animationMinScale = 0.525f

        try {
            this.apply {
                isClickable = false
                animate().run {
                    scaleXBy(1f)
                    scaleX(animationMinScale)
                    scaleYBy(1f)
                    scaleY(animationMinScale)
                    duration = toggleDuration.toLong()
                    setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: android.animation.Animator) {
                            super.onAnimationEnd(animation)
                            animate().run {
                                scaleX(1f)
                                scaleY(1f)
                                duration = toggleDuration.toLong()
                                setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(
                                        animation: android.animation.Animator
                                    ) {
                                        super.onAnimationEnd(animation)
                                        isClickable = true
                                        function.invoke(this@apply)
                                    }
                                })
                            }
                        }
                    })
                }
            }
        } catch (e: Exception) {
            isClickable = true
            function.invoke(this)
        }
    }
    return this
}

fun View.setMargin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
