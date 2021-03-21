package com.beomjo.whitenoise.ui.main

import android.os.Bundle
import com.beomjo.whitenoise.R
import com.beomjo.whitenoise.base.BaseActivity
import com.beomjo.whitenoise.databinding.ActivityMainBinding
import com.beomjo.whitenoise.utilities.ext.applyExitMaterialTransform
import com.beomjo.whitenoise.utilities.ext.getComponent

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)
    }

    override fun inject() {
        application.getComponent().mainComponent().create().inject(this)
    }
}