package com.beomjo.whitenoise.ui.category

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.view.Window
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityCategoryListBinding
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.getComponent
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import javax.inject.Inject

class CategoryListActivity : BaseActivity<ActivityCategoryListBinding>(
    R.layout.activity_category_list,
) {

    @Inject
    lateinit var playerManager: PlayerManager

    private val homeCategoryMeta: HomeCategory by lazy {
        intent.getParcelableExtra(EXTRA_HOME_CATEGORY)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        applyMaterialTransform(binding.root, homeCategoryMeta.id.toString())
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    override fun inject() {
        application.getComponent().categoryComponent().create().inject(this)
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager
            activity = this@CategoryListActivity
            homeCategory = homeCategoryMeta
        }
    }

    companion object {
        private const val EXTRA_HOME_CATEGORY = "HOME_CATEGORY"

        fun startActivity(
            context: Context?,
            startView: View,
            homeCategory: HomeCategory,
        ) {
            context?.let {
                val activity = it as Activity
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    Pair(startView, homeCategory.id.toString()),
                )
                val intent = Intent(context, CategoryListActivity::class.java).apply {
                    putExtra(EXTRA_HOME_CATEGORY, homeCategory)
                }
                activity.startActivity(intent, options.toBundle())
            }
        }
    }
}