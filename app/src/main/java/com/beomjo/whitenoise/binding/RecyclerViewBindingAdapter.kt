package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.adapters.HomeAdapter


object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterHomeCategories")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, homeCategories: List<HomeCategory>?) {
        homeCategories?.let { (recyclerView.adapter as HomeAdapter).addItems(homeCategories) }
    }
}