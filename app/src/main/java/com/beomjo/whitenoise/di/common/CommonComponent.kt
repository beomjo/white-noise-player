package com.beomjo.whitenoise.di.common

import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import dagger.Subcomponent

@Subcomponent
interface CommonComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CommonComponent
    }

    fun inject(fragment: ProgressDialogFragment)
}