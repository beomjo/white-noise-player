package com.beomjo.whitenoise.di.category

import com.beomjo.whitenoise.ui.category.CategoryActivity
import dagger.Subcomponent

@Subcomponent
interface CategoryComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CategoryComponent
    }

    fun inject(activity: CategoryActivity)
}