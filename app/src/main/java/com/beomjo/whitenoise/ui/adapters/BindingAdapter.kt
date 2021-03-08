package com.beomjo.whitenoise.ui.adapters

import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("android:onClick")
    fun bindSignInClick(button: SignInButton, method: () -> Unit) {
        button.setOnClickListener { method.invoke() }
    }
}