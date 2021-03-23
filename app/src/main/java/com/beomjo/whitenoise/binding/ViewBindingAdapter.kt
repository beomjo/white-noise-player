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