package com.beomjo.whitenoise.ui.category

import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.repositories.category.CategoryRepository
import javax.inject.Inject

class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

}