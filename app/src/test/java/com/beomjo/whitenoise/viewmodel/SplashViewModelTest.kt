package com.beomjo.whitenoise.viewmodel

import com.beomjo.compilation.util.getOrAwaitValue
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.ui.splash.LoginBefore
import com.beomjo.whitenoise.ui.splash.LoginSuccess
import com.beomjo.whitenoise.ui.splash.SplashViewModel
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

class SplashViewModelTest : BaseTest(){

    @Mock
    lateinit var authRepository: AuthRepository

    lateinit var viewModel: SplashViewModel

    override fun onBefore() {
        viewModel = spy(SplashViewModel(authRepository))
    }

    @Test
    fun `로그인여부 체크, 로그인되지않음`() {
        //given
        `when`(authRepository.isLoggedIn()).thenReturn(false)

        //when
        viewModel.check()

        //then
        assertEquals(viewModel.loginState.getOrAwaitValue(), LoginBefore)
        verify(authRepository).isLoggedIn()
    }

    @Test
    fun `로그인여부 체크, 로그인됨`() {
        //given
        `when`(authRepository.isLoggedIn()).thenReturn(true)

        //when
        viewModel.check()

        //then
        assertEquals(viewModel.loginState.getOrAwaitValue(), LoginSuccess)
        verify(authRepository).isLoggedIn()
    }
}
