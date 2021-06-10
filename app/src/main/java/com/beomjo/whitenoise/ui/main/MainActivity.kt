package com.beomjo.whitenoise.ui.main

import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityMainBinding
import com.beomjo.whitenoise.di.main.MainComponent
import com.beomjo.whitenoise.ui.player.PlayerActivity
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.applyExitMaterialTransform
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(
    R.layout.activity_main,
    MainViewModel::class
) {
    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface MainEntryPoints {
        fun mainComponent(): MainComponent.Factory
    }


    @Inject
    lateinit var playerManager: PlayerManager

    private val mainViewModel: MainViewModel by getViewModel()

    override fun inject() {
        val entryPoint =
            EntryPointAccessors.fromApplication(applicationContext, MainEntryPoints::class.java)
        entryPoint.mainComponent().create()
        val playerEntryPoint =
            EntryPointAccessors.fromApplication(
                applicationContext,
                PlayerActivity.PlayerEntryPoints::class.java
            )
        playerManager = playerEntryPoint.playerManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)
        bindingViewModel()
        observePlayer()
    }

    private fun bindingViewModel() {
        binding {
            lifecycleOwner = this@MainActivity
            viewmodel = mainViewModel
            activity = this@MainActivity
            manager = playerManager.apply { init() }
        }
    }

    private fun observePlayer() {
        with(playerManager) {
            moveToPlayerActivity.observe(this@MainActivity) { sound ->
                PlayerActivity.startActivity(
                    context = this@MainActivity,
                    startView = binding.root.findViewById(R.id.player_container_layout),
                    track = sound,
                    isClickBottomPlayer = true,
                )
            }
        }
    }

    override fun onDestroy() {
        playerManager.onCleared()
        super.onDestroy()
    }
}