package com.beomjo.whitenoise.ui.player

import android.app.Activity
import android.app.ActivityOptions
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
import com.beomjo.whitenoise.model.Sound
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import com.beomjo.whitenoise.utilities.ext.getComponent
import javax.inject.Inject

class PlayerActivity : BaseActivity<ActivityPlayerBinding>(
    R.layout.activity_player
) {

    @Inject
    lateinit var playerManager: PlayerManager

    private val sound: Sound by lazy { intent.getParcelableExtra(KEY_PLAYER_SOUND)!! }

    override fun inject() {
        application.getComponent().playerComponent().create().inject(this)
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarColor(sound.getBackgroundColor())
        } else {
            setStatusBarColor(Color.WHITE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        applyMaterialTransform(binding.root, TRANSITION_NAME)
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager.apply { setSound(this@PlayerActivity.sound) }
            activity = this@PlayerActivity
        }
    }

    companion object {
        private const val TRANSITION_NAME = "PLAYER_TRANSITION"
        private const val KEY_PLAYER_SOUND = "KEY_PLAYER_SOUND"

        fun startActivity(
            context: Context?,
            startView: View,
            sound: Sound,
        ) {
            context?.let {
                val activity = it as Activity
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair(startView, TRANSITION_NAME),
                )
                val intent = Intent(context, PlayerActivity::class.java).apply {
                    putExtra(KEY_PLAYER_SOUND, sound)
                }
                activity.startActivity(intent, options.toBundle())
            }
        }
    }
}