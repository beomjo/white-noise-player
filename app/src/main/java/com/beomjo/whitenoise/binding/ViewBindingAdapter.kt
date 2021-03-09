package com.beomjo.whitenoise.binding

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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
}