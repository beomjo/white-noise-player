package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.Sound
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.ui.adapters.SoundListAdapter
import com.beomjo.whitenoise.ui.adapters.HomeAdapter

object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterCategoryList")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, categories: List<Category>?) {
        categories?.let { (recyclerView.adapter as HomeAdapter).addItems(it) }
    }

    @JvmStatic
    @BindingAdapter("adapterSoundList")
    fun bindAdapterCategoryItemList(recyclerView: RecyclerView, sounds: List<Sound>?) {
        sounds?.let { (recyclerView.adapter as SoundListAdapter).addItems(it) }
    }
}