package com.beomjo.whitenoise.di.category

import com.beomjo.whitenoise.ui.main.category.CategoryListFragment
import dagger.Subcomponent

@Subcomponent
interface CategoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoryComponent
    }

    fun inject(fragment: CategoryListFragment)
}