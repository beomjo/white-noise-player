package com.beomjo.whitenoise.viewmodel

import androidx.lifecycle.Observer
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.repositories.track.TrackListRepository
import com.beomjo.whitenoise.ui.main.track.TrackListViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class TrackListViewModelTest : BaseTest() {

    @MockK
    private lateinit var trackListRepository: TrackListRepository

    private lateinit var viewModel: TrackListViewModel

    override fun onBefore() {
        viewModel = spyk(TrackListViewModel(trackListRepository), recordPrivateCalls = true)
    }

    @Test
    fun `카테고리 아이템 목록 불러오기 성공`() {
        //given
        val documentPath = "pathpath"
        val mockCategory = mockk<Track>()
        val categories = listOf(mockCategory, mockCategory)
        coEvery { trackListRepository.getTrackList(documentPath) } returns categories
        val categoriesObserver = mockk<Observer<List<Track>>>() {
            every { onChanged(categories) } just Runs
        }
        viewModel.tracks.observeForever(categoriesObserver)

        //when
        viewModel.loadTrackList(documentPath)

        //then
        coVerify { categoriesObserver.onChanged(eq(categories)) }
    }
}