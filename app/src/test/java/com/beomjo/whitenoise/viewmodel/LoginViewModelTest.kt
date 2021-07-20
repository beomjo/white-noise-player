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

import android.content.Intent
import androidx.lifecycle.Observer
import com.beomjo.compilation.util.Event
import com.beomjo.whitenoise.BaseTest
import com.beomjo.whitenoise.exceptions.FirebaseAccountNotFoundException
import com.beomjo.whitenoise.exceptions.IdTokenNotFoundException
import com.beomjo.whitenoise.repositories.auth.AuthRepository
import com.beomjo.whitenoise.ui.auth.LoginViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@Suppress("NonAsciiCharacters")
@ExperimentalCoroutinesApi
@FlowPreview
class LoginViewModelTest : BaseTest() {

    @MockK
    lateinit var authRepository: AuthRepository

    private lateinit var viewModel: LoginViewModel

    override fun onBefore() {
        viewModel = spyk(LoginViewModel(authRepository))
    }

    @Test
    fun `구글로그인 버튼 클릭하였을때 인텐트를 전달한다`() {
        // given
        val mockIntent = mockk<Intent>()
        every { authRepository.getGoogleSinInIntent() } returns mockIntent
        val observer = mockk<Observer<Intent>> { every { onChanged(mockIntent) } just Runs }
        viewModel.googleLoginIntent.observeForever(observer)

        // when
        viewModel.onClickGoogleLogin()

        // then
        verify { observer.onChanged(eq(mockIntent)) }
    }

    @Test
    fun `구글로그인 성공`() {
        coroutineScope.runBlockingTest {
            // given
            val idToken = "falkland"
            val mockIntent = mockk<Intent>()
            val mockCredential = mockk<AuthCredential>()
            val mockAuthResult = mockk<AuthResult>()
            val isLoginSuccess = true
            every { authRepository.getIdTokenFromIntent(mockIntent) } returns flow { emit(idToken) }
            every { authRepository.getGoogleCredential(idToken) } returns flow { emit(mockCredential) }
            every { authRepository.loginWithCredential(mockCredential) } returns
                flow { emit(mockAuthResult) }
            val loginSuccessObserver = mockk<Observer<Boolean>> {
                every { onChanged(isLoginSuccess) } just Runs
            }
            val progressObserver = mockk<Observer<Event<Boolean>>> {
                every { onChanged(Event(true)) } just Runs
                every { onChanged(Event(false)) } just Runs
            }
            viewModel.loginSuccess.observeForever(loginSuccessObserver)
            viewModel.progress.observeForever(progressObserver)

            // when
            viewModel.processGoogleLogin(mockIntent)

            // then
            verifyOrder {
                progressObserver.onChanged(eq(Event(true)))
                authRepository.getIdTokenFromIntent(mockIntent)
                authRepository.getGoogleCredential(idToken)
                authRepository.loginWithCredential(mockCredential)
                loginSuccessObserver.onChanged(eq(isLoginSuccess))
                progressObserver.onChanged(eq(Event(false)))
            }
        }
    }

    @Test
    fun `intent에 idToken데이터가 없다면 구글로그인에 실패해야한다`() {
        coroutineScope.runBlockingTest {
            // given
            val exception = IdTokenNotFoundException()
            val errorMsg = exception.cause?.message ?: "Fail"
            val mockIntent = mockk<Intent>()
            every { authRepository.getIdTokenFromIntent(mockIntent) } returns
                flow { error(exception) }
            val toastObserver = mockk<Observer<Event<String>>> {
                every { onChanged(Event(errorMsg)) } just Runs
            }
            val progressObserver = mockk<Observer<Event<Boolean>>> {
                every { onChanged(Event(true)) } just Runs
                every { onChanged(Event(false)) } just Runs
            }
            viewModel.toast.observeForever(toastObserver)
            viewModel.progress.observeForever(progressObserver)

            // when
            viewModel.processGoogleLogin(mockIntent)

            // then
            verifyOrder {
                progressObserver.onChanged(eq(Event(true)))
                authRepository.getIdTokenFromIntent(mockIntent)
                authRepository.getGoogleCredential(any()) wasNot Called
                authRepository.loginWithCredential(any()) wasNot Called
                toastObserver.onChanged(eq(Event(errorMsg)))
                progressObserver.onChanged(eq(Event(false)))
            }
        }
    }

    @Test
    fun `Credential을 전달하여 구글 로그인 요청, 에러발생하여 실패해야한다`() {
        // given
        val exception = IdTokenNotFoundException()
        val errorMsg = exception.cause?.message ?: "Fail"
        val idToken = "falkland"
        val mockIntent = mockk<Intent>()
        val mockCredential = mockk<AuthCredential>()
        every { authRepository.getIdTokenFromIntent(mockIntent) } returns flow { emit(idToken) }
        every { authRepository.getGoogleCredential(idToken) } returns flow { emit(mockCredential) }
        every { authRepository.loginWithCredential(mockCredential) } returns
            flow { error(FirebaseAccountNotFoundException()) }
        val toastObserver = mockk<Observer<Event<String>>> {
            every { onChanged(Event(errorMsg)) } just Runs
        }
        val progressObserver = mockk<Observer<Event<Boolean>>> {
            every { onChanged(Event(true)) } just Runs
            every { onChanged(Event(false)) } just Runs
        }
        viewModel.toast.observeForever(toastObserver)
        viewModel.progress.observeForever(progressObserver)

        // when
        viewModel.processGoogleLogin(mockIntent)

        // then
        verifyOrder {
            progressObserver.onChanged(eq(Event(true)))
            authRepository.getIdTokenFromIntent(mockIntent)
            authRepository.getGoogleCredential(idToken)
            authRepository.loginWithCredential(mockCredential)
            toastObserver.onChanged(eq(Event(errorMsg)))
            progressObserver.onChanged(eq(Event(false)))
        }
    }
}
