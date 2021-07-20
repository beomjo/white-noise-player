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
