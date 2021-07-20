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

package com.beomjo.whitenoise

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.MockKAnnotations
import org.junit.After
import org.junit.Before
import org.mockito.Mockito

abstract class MockitoTest {
    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        onBefore()
    }

    @After
    fun tearDown() {
        onAfter()
    }

    protected abstract fun onBefore()

    protected fun onAfter() {
        // Do Nothing
    }

    protected val context: Context
        get() = ApplicationProvider.getApplicationContext()

    protected fun getString(resId: Int): String {
        return context.getString(resId)
    }

    protected inline fun <reified T> any(): T {
        Mockito.any<T>()
        return null as T
    }
}
