/*
 * Designed and developed by 2021 beomjo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        // given
        val documentPath = "pathpath"
        val mockCategory = mockk<Track>()
        val categories = listOf(mockCategory, mockCategory)
        coEvery { trackListRepository.getTrackList(documentPath) } returns categories
        val categoriesObserver = mockk<Observer<List<Track>>>() {
            every { onChanged(categories) } just Runs
        }
        viewModel.tracks.observeForever(categoriesObserver)

        // when
        viewModel.loadTrackList(documentPath)

        // then
        coVerify { categoriesObserver.onChanged(eq(categories)) }
    }
}
