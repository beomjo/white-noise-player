package com.beomjo.whitenoise.common

import com.beomjo.compilation.env.Config
import com.beomjo.whitenoise.BuildConfig

class WhiteNoseConfig : Config() {
    override val LOG_VISIBLE: Boolean
        get() = BuildConfig.DEBUG
}
