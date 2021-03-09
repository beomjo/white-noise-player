package com.beomjo.whitenoise.ui.category

import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityCategoryBinding
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.getComponent
import javax.inject.Inject

class CategoryActivity : BaseActivity<ActivityCategoryBinding>(
    R.layout.activity_category,
) {

    @Inject
    lateinit var playerManager: PlayerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    override fun inject() {
        application.getComponent().categoryComponent().create().inject(this)
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager
        }
    }
}