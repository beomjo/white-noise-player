package com.beomjo.whitenoise.viewmodel

import com.beomjo.compilation.util.getOrAwaitValue
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.ui.splash.LoginBefore
import com.beomjo.whitenoise.ui.splash.LoginSuccess
import com.beomjo.whitenoise.ui.splash.SplashViewModel
import com.beomjo.whitenoise.ui.splash.SplashViewModel.Companion.SPLASH_DELAY_TIME_MILLIS
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
@Suppress("NonAsciiCharacters")
class SplashViewModelTest : BaseTest() {

    @MockK
    lateinit var authRepository: AuthRepository

    lateinit var viewModel: SplashViewModel

    override fun onBefore() {
        viewModel = spyk(SplashViewModel(authRepository))
    }

    @Test
    fun `로그인여부 체크, 로그인되지않음`() = runBlockingTest {
        //given
        every { authRepository.isLoggedIn() } returns false

        //when
        viewModel.check()
        advanceTimeBy(SPLASH_DELAY_TIME_MILLIS)

        //then
        verify { authRepository.isLoggedIn() }
        assertEquals(viewModel.loginState.getOrAwaitValue(), LoginBefore)
    }

    @Test
    fun `로그인여부 체크, 로그인됨`() = runBlockingTest {
        //given
        every { authRepository.isLoggedIn() } returns true

        //when
        viewModel.check()
        advanceTimeBy(SPLASH_DELAY_TIME_MILLIS)

        //then
        verify { authRepository.isLoggedIn() }
        assertEquals(viewModel.loginState.getOrAwaitValue(), LoginSuccess)
    }
}
