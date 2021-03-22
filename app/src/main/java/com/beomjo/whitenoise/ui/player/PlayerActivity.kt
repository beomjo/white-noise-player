package com.beomjo.whitenoise.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityPlayerBinding
import com.beomjo.whitenoise.utilities.ext.getComponent
import javax.inject.Inject

class PlayerActivity : BaseActivity<ActivityPlayerBinding>(
    R.layout.activity_player
) {

    @Inject
    lateinit var playerManager: PlayerManager

    override fun inject() {
        application.getComponent().playerComponent().create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager
        }
    }

    companion object {
        fun startActivity(context: Context, intent: Intent) {
            context.startActivity(intent)
        }
    }
}