package com.beomjo.compilation

import android.content.Context
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import androidx.test.core.app.ApplicationProvider
import org.mockito.junit.MockitoRule

abstract class MockitoTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        onBefore()
    }

    @After
    fun tearDown() {
        Mockito.validateMockitoUsage()
        onAfter()
    }

    protected abstract fun onBefore()

    protected fun onAfter() {}

    protected val context: Context
        get() = ApplicationProvider.getApplicationContext()

    protected fun getString(resId: Int): String {
        return context.getString(resId)
    }
}
