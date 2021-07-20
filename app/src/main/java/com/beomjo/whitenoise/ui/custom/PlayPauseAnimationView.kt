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
