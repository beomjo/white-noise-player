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
import com.beomjo.compilation.util.Event
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.User
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.repositories.home.HomeRepository
import com.beomjo.whitenoise.ui.main.home.HomeViewModel
import com.google.firebase.FirebaseException
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Test

class HomeViewModelTest : BaseTest() {

    @MockK
    lateinit var authRepository: AuthRepository

    @MockK
    lateinit var homeRepository: HomeRepository

    private lateinit var viewModel: HomeViewModel

    override fun onBefore() {
        viewModel = spyk(
            HomeViewModel(authRepository, homeRepository),
            recordPrivateCalls = true,
        )
    }

    @Test
    fun `init() 호출`() {
        // given
        justRun { viewModel invokeNoArgs "loadUserInfo" }
        justRun { viewModel invokeNoArgs "loadHomeCategoryList" }

        // when
        viewModel.init()

        // then
        verify { viewModel invokeNoArgs "loadUserInfo" }
        verify { viewModel invokeNoArgs "loadHomeCategoryList" }
    }

    @Test
    fun `유저 정보 로드 성공`() {
        // given
        val mockUser = mockk<User>()
        every { authRepository.getUserInfo() } returns mockUser
        val userObserver = mockk<Observer<User>>(relaxed = true)
        viewModel.user.observeForever(userObserver)

        // when
        viewModel invokeNoArgs "loadUserInfo"

        // then
        verify { authRepository.getUserInfo() }
        verify { userObserver.onChanged(eq(mockUser)) }
    }

    @Test
    fun `유저 정보 로드 실패`() {
        // given
        val exception = mockk<FirebaseException>()
        val errorMsg = "Fail"
        every { exception.message } returns errorMsg
        every { authRepository.getUserInfo() } throws exception
        val userObserver = mockk<Observer<User>>()
        val toastObserver = mockk<Observer<Event<String>>>(relaxed = true)
        viewModel.user.observeForever(userObserver)
        viewModel.toast.observeForever(toastObserver)

        // when
        viewModel invokeNoArgs "loadUserInfo"

        // then
        verify { authRepository.getUserInfo() }
        verify { userObserver wasNot Called }
        verify { toastObserver.onChanged(eq(Event(errorMsg))) }
    }

    @Test
    fun `홈 카테고리 데이터 로드 성공`() {
        // given
        val homeCategory = mockk<Category>()
        val homeCategories = listOf(homeCategory)
        coEvery { homeRepository.getHomeCategoryList() } returns homeCategories
        val loadingObserver = mockk<Observer<Boolean>>(relaxed = true)
        val refreshObserver = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isLoading.observeForever(loadingObserver)
        viewModel.isRefresh.observeForever(refreshObserver)

        // when
        viewModel invokeNoArgs "loadHomeCategoryList"

        // then
        coVerifyOrder {
            loadingObserver.onChanged(true)
            homeRepository.getHomeCategoryList()
            loadingObserver.onChanged(false)
            refreshObserver.onChanged(false)
        }
    }

    @Test
    fun `홈 카테고리 데이터 로드 실패`() {
        // given
        val exception = mockk<FirebaseException>()
        val errorMsg = "Fail"
        every { exception.message } returns errorMsg
        coEvery { homeRepository.getHomeCategoryList() } throws exception
        val loadingObserver = mockk<Observer<Boolean>>(relaxed = true)
        val toastObserver = mockk<Observer<Event<String>>> {
            every { onChanged(Event(errorMsg)) } just Runs
        }
        viewModel.toast.observeForever(toastObserver)
        viewModel.isLoading.observeForever(loadingObserver)

        // when
        viewModel invokeNoArgs "loadHomeCategoryList"

        // then
        coVerifyOrder {
            loadingObserver.onChanged(true)
            homeRepository.getHomeCategoryList()
            toastObserver.onChanged(eq(Event(errorMsg)))
        }
    }

    @Test
    fun `Refresh 하여 재로딩`() {
        // given
        justRun { viewModel invokeNoArgs "loadHomeCategoryList" }
        val refreshObserver = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isRefresh.observeForever(refreshObserver)

        // when
        viewModel.onRefresh()

        // then
        verify { refreshObserver.onChanged(true) }
        verify { viewModel invokeNoArgs "loadHomeCategoryList" }
    }
}
