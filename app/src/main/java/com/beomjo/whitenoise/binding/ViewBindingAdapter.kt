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

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.beomjo.whitenoise.utilities.ext.setOnClickAnimation
import com.bumptech.glide.Glide

object ViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImageUrl(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(it)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImageFromUri(view: ImageView, uri: Uri?) {
        uri?.let {
            Glide.with(view.context)
                .load(it.toString())
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("isSelected")
    fun bindSetSelect(view: View, isSelected: Boolean) {
        view.isSelected = isSelected
    }

    @JvmStatic
    @BindingAdapter("onClickWithAnimation")
    fun onClickWithAnimation(view: View, listener: View.OnClickListener) {
        view.setOnClickAnimation { listener.onClick(view) }
    }
}
