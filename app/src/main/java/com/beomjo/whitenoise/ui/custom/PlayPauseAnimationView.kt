package com.beomjo.whitenoise.ui.custom

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.ui.player.SoundPause
import com.beomjo.whitenoise.ui.player.SoundPlaying
import com.beomjo.whitenoise.ui.player.SoundState


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

    fun setState(state: SoundState) {
        when (state) {
            SoundPlaying -> {
                this.setImageDrawable(playToPauseAnim)
                playToPauseAnim?.start()
            }
            SoundPause -> {
                this.setImageDrawable(pauseToPlay)
                pauseToPlay?.start()
            }
        }
    }
}