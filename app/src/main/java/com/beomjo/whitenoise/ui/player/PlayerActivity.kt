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

package com.beomjo.whitenoise.ui.player

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.Window
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityPlayerBinding
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : BaseActivity<ActivityPlayerBinding>(
    R.layout.activity_player
) {

    @Inject
    lateinit var playerManager: PlayerManager

    private val track: Track by lazy { intent.getParcelableExtra(KEY_PLAYER_TRACK)!! }
    private val isEnterFromBottomPlayer: Boolean
        by lazy { intent.getBooleanExtra(KEY_BOTTOM_PLAYER_CLICK, false) }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarColor(track.getMixedBackgroundColor())
        } else {
            setStatusBarColor(Color.WHITE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        applyMaterialTransform(binding.root, TRANSITION_NAME)
        super.onCreate(savedInstanceState)
        bindingViewModel()
        initPlayer()
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager
            activity = this@PlayerActivity
        }
    }

    private fun initPlayer() {
        with(playerManager) {
            if (!isEnterFromBottomPlayer) {
                setTrack(this@PlayerActivity.track)
            }
        }
    }

    companion object {
        private const val TRANSITION_NAME = "PLAYER_TRANSITION"
        const val KEY_PLAYER_TRACK = "KEY_PLAYER_TRACK"
        const val KEY_BOTTOM_PLAYER_CLICK = "KEY_BOTTOM_PLAYER_CLICK"

        fun startActivity(
            context: Context?,
            startView: View,
            track: Track,
            isClickBottomPlayer: Boolean = false,
        ) {
            context?.let {
                val activity = it as Activity
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair(startView, TRANSITION_NAME),
                )
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    putExtra(KEY_PLAYER_TRACK, track)
                    putExtra(KEY_BOTTOM_PLAYER_CLICK, isClickBottomPlayer)
                }
                activity.startActivity(intent, options.toBundle())
            }
        }
    }
}
