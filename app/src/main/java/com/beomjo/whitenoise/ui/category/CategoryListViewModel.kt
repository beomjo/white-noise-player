package com.beomjo.whitenoise.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.beomjo.whitenoise.base.BaseViewModel
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.repositories.category.CategoryRepository
import javax.inject.Inject

class CategoryListViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun loadCategoryList(documentPath: String) {
        launch {
            _categories.value = categoryRepository.getCategories(documentPath)
        }
    }
}