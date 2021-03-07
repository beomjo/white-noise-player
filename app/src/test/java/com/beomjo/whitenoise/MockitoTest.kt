package com.beomjo.whitenoise

import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import androidx.test.core.app.ApplicationProvider
import io.mockk.MockKAnnotations
import org.mockito.junit.MockitoRule

abstract class MockitoTest {
    @Before
    fun setup() {
        MockKAnnotations.init(this)
        onBefore()
    }

    @After
    fun tearDown() {
        onAfter()
    }

    protected abstract fun onBefore()

    protected fun onAfter() {}

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
