package com.beomjo.whitenoise.viewmodel

import androidx.lifecycle.Observer
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.model.Sound
import com.beomjo.whitenoise.repositories.category.SoundListRepository
import com.beomjo.whitenoise.ui.main.sound.SoundListViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class CategoryListViewModelTest : BaseTest() {

    @MockK
    private lateinit var soundListRepository: SoundListRepository

    private lateinit var viewModel: SoundListViewModel

    override fun onBefore() {
        viewModel = spyk(SoundListViewModel(soundListRepository), recordPrivateCalls = true)
    }

    @Test
    fun `카테고리 아이템 목록 불러오기 성공`() {
        //given
        val documentPath = "pathpath"
        val mockCategory = mockk<Sound>()
        val categories = listOf(mockCategory, mockCategory)
        coEvery { soundListRepository.getSounds(documentPath) } returns categories
        val categoriesObserver = mockk<Observer<List<Sound>>>() {
            every { onChanged(categories) } just Runs
        }
        viewModel.sounds.observeForever(categoriesObserver)

        //when
        viewModel.loadCategoryList(documentPath)

        //then
        coVerify { categoriesObserver.onChanged(eq(categories)) }
    }
}