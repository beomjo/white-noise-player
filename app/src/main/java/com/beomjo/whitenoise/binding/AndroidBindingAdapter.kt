package com.beomjo.whitenoise.binding

import android.view.Gravity
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.gms.common.SignInButton



object AndroidBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:onClick")
    fun bindSignInClick(button: SignInButton, method: () -> Unit) {
        button.setOnClickListener { method.invoke() }
    }

    @JvmStatic
    @BindingAdapter("android:texts")
    fun bindSignInButtonText(button: SignInButton, res: Int) {
        repeat(button.childCount) {
            val v = button.getChildAt(it)
            if (v is TextView) {
                v.text = v.context.getText(res)
                v.gravity = Gravity.CENTER
            }
        }
    }
}
