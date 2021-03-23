package com.beomjo.whitenoise.utilities.ext

import android.animation.AnimatorListenerAdapter
import android.view.View

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
                                    override fun onAnimationEnd(animation: android.animation.Animator) {
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