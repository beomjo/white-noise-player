package com.beomjo.whitenoise.viewmodel

import android.content.Intent
import com.beomjo.compilation.util.getOrAwaitValue
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.exceptions.FirebaseAccountNotFoundException
import com.beomjo.whitenoise.exceptions.IdTokenNotFoundException
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.ui.auth.LoginViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

@FlowPreview
class LoginViewModelTest : BaseTest() {

    @Mock
    lateinit var authRepository: AuthRepository

    lateinit var viewModel: LoginViewModel

    override fun onBefore() {
        viewModel = spy(LoginViewModel(authRepository))
    }

    @Test
    fun `구글로그인 버튼 클릭하였을때 인텐트를 전달한다`() {
        //given
        val mockIntent = mock(Intent::class.java)
        `when`(authRepository.getGoogleSinInIntent()).thenReturn(mockIntent)

        //when
        viewModel.onClickGoogleLogin()

        //then
        assertEquals(viewModel.googleLoginIntent.getOrAwaitValue(), mockIntent)
    }

    @Test
    fun `구글로그인 성공`() {
        coroutineScope.runBlockingTest {
            //given
            val idToken = "falkland"
            val mockIntent = mock(Intent::class.java)
            val mockCredential = mock(AuthCredential::class.java)
            val mockAuthResult = mock(AuthResult::class.java)
            `when`(authRepository.getIdTokenFromIntent(mockIntent))
                .thenReturn(flow { emit(idToken) })
            `when`(authRepository.getGoogleCredential(idToken))
                .thenReturn(flow { emit(mockCredential) })
            `when`(authRepository.loginWithCredential(mockCredential))
                .thenReturn(flow { emit(mockAuthResult) })

            //when
            viewModel.processGoogleLogin(mockIntent)

            //then
            assertEquals(viewModel.loginSuccess.getOrAwaitValue(), true)
            verify(authRepository).getIdTokenFromIntent(mockIntent)
            verify(authRepository).getGoogleCredential(idToken)
            verify(authRepository).loginWithCredential(mockCredential)
        }
    }

    @Test
    fun `intent에 idToken데이터가 없다면 구글로그인에 실패해야한다`() {
        coroutineScope.runBlockingTest {
            //given
            val mockIntent = mock(Intent::class.java)
            `when`(authRepository.getIdTokenFromIntent(mockIntent))
                .thenReturn(flow { error(IdTokenNotFoundException()) })

            //when
            viewModel.processGoogleLogin(mockIntent)

            //then
            verify(authRepository).getIdTokenFromIntent(mockIntent)
            verify(authRepository, never()).getGoogleCredential(anyString())
            verify(authRepository, never()).loginWithCredential(any())
        }
    }

    @Test
    fun `Credential을 전달하여 구글 로그인 요청, 에러발생하여 실패해야한다`() {
        //given
        val idToken = "falkland"
        val mockIntent = mock(Intent::class.java)
        val mockCredential = mock(AuthCredential::class.java)
        val mockAuthResult = mock(AuthResult::class.java)
        `when`(authRepository.getIdTokenFromIntent(mockIntent))
            .thenReturn(flow { emit(idToken) })
        `when`(authRepository.getGoogleCredential(idToken))
            .thenReturn(flow { emit(mockCredential) })
        `when`(authRepository.loginWithCredential(mockCredential))
            .thenReturn(flow { error(FirebaseAccountNotFoundException()) })

        //when
        viewModel.processGoogleLogin(mockIntent)

        //then
        verify(authRepository).getIdTokenFromIntent(mockIntent)
        verify(authRepository).getGoogleCredential(idToken)
        verify(authRepository).loginWithCredential(mockCredential)
    }
}

