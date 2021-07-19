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
