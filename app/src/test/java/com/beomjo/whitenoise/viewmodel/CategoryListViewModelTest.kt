package com.beomjo.whitenoise.viewmodel

import androidx.lifecycle.Observer
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.repositories.category.CategoryRepository
import com.beomjo.whitenoise.ui.category.CategoryListViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class CategoryListViewModelTest : BaseTest() {

    @MockK
    private lateinit var categoryRepository: CategoryRepository

    private lateinit var viewModel: CategoryListViewModel

    override fun onBefore() {
        viewModel = spyk(CategoryListViewModel(categoryRepository), recordPrivateCalls = true)
    }

    @Test
    fun `카테고리 아이템 목록 불러오기 성공`() {
        //given
        val documentPath = "pathpath"
        val mockCategory = mockk<Category>()
        val categories = listOf(mockCategory, mockCategory)
        coEvery { categoryRepository.getCategories(documentPath) } returns categories
        val categoriesObserver = mockk<Observer<List<Category>>>() {
            every { onChanged(categories) } just Runs
        }
        viewModel.categories.observeForever(categoriesObserver)

        //when
        viewModel.loadCategoryList(documentPath)

        //then
        coVerify { categoriesObserver.onChanged(eq(categories)) }
    }
}