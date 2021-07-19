package com.beomjo.whitenoise.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.beomjo.whitenoise.R

class PlayPauseAnimationView : AppCompatImageView {
    private var playToPauseAnim: AnimatedVectorDrawableCompat? = null
    private var pauseToPlay: AnimatedVectorDrawableCompat? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        playToPauseAnim = AnimatedVectorDrawableCompat.create(context, R.drawable.play_to_pause)
        pauseToPlay = AnimatedVectorDrawableCompat.create(context, R.drawable.pause_to_play)
    }

    fun isPlaying(isPlaying: Boolean) {
        if (isPlaying) {
            this.setImageDrawable(playToPauseAnim)
            playToPauseAnim?.start()
        } else {
            this.setImageDrawable(pauseToPlay)
            pauseToPlay?.start()
        }
    }
}
