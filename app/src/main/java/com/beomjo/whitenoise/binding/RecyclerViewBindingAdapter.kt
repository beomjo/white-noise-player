package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.Track
import com.beomjo.whitenoise.ui.adapters.HomeAdapter
import com.beomjo.whitenoise.ui.adapters.TrackListAdapter

object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterCategoryList")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, categories: List<Category>?) {
        categories?.let { (recyclerView.adapter as HomeAdapter).addItems(it) }
    }

    @JvmStatic
    @BindingAdapter("adapterTrackList")
    fun bindAdapterCategoryItemList(recyclerView: RecyclerView, tracks: List<Track>?) {
        tracks?.let { (recyclerView.adapter as TrackListAdapter).addItems(it) }
    }
}