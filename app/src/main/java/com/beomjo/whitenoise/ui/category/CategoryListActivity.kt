package com.beomjo.whitenoise.ui.category

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
import com.beomjo.whitenoise.databinding.ActivityCategoryListBinding
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.adapters.CategoryListAdapter
import com.beomjo.whitenoise.ui.player.PlayerManager
import com.beomjo.whitenoise.utilities.ext.applyMaterialTransform
import com.beomjo.whitenoise.utilities.ext.getComponent
import javax.inject.Inject

class CategoryListActivity : BaseActivity<ActivityCategoryListBinding>(
    R.layout.activity_category_list,
    CategoryListViewModel::class
) {

    @Inject
    lateinit var playerManager: PlayerManager

    private val homeCategoryMeta: HomeCategory by lazy {
        intent.getParcelableExtra(EXTRA_HOME_CATEGORY)!!
    }

    private val categoryListViewModel: CategoryListViewModel by getViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        applyMaterialTransform(binding.root, homeCategoryMeta.id.toString())
        super.onCreate(savedInstanceState)
        bindingViewModel()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarColor(homeCategoryMeta.getPrimaryColor())
        } else {
            setStatusBarColor(Color.WHITE)
        }
    }

    override fun inject() {
        application.getComponent().categoryComponent().create().inject(this)
    }

    private fun bindingViewModel() {
        binding {
            manager = playerManager
            activity = this@CategoryListActivity
            adapter = CategoryListAdapter(object :
                CategoryListAdapter.CategoryItemViewHolder.OnClickListener {
                override fun onItemClick(view: View, item: Category) {

                }
            })
            homeCategory = homeCategoryMeta
            viewmodel = categoryListViewModel.apply { loadCategoryList(homeCategoryMeta.path) }
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