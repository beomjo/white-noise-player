package com.beomjo.whitenoise.di.category

import com.beomjo.whitenoise.ui.category.CategoryListActivity
import dagger.Subcomponent

@Subcomponent
interface CategoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoryComponent
    }

    fun inject(activity: CategoryListActivity)
}