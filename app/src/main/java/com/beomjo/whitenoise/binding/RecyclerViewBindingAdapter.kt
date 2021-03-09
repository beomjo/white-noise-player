package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.HomeItem
import com.beomjo.whitenoise.ui.adapters.HomeAdapter


object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterHomeItemList")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, homeItems: List<HomeItem>?) {
        homeItems?.let { (recyclerView.adapter as HomeAdapter).addItems(homeItems) }
    }
}