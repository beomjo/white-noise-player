package com.beomjo.whitenoise.di.common

import com.beomjo.whitenoise.ui.common.ProgressDialogFragment
import dagger.Subcomponent
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Subcomponent
interface CommonComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CommonComponent
    }
}