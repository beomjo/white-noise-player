package com.beomjo.whitenoise.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.model.Category
import com.beomjo.whitenoise.model.HomeCategory
import com.beomjo.whitenoise.ui.adapters.CategoryListAdapter
import com.beomjo.whitenoise.ui.adapters.HomeAdapter


object RecyclerViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("adapterHomeCategories")
    fun bindAdapterHomeItemList(recyclerView: RecyclerView, homeCategories: List<HomeCategory>?) {
        homeCategories?.let { (recyclerView.adapter as HomeAdapter).addItems(it) }
    }

    @JvmStatic
    @BindingAdapter("adapterCategories")
    fun bindAdapterCategoryItemList(recyclerView: RecyclerView, categories: List<Category>?) {
        categories?.let { (recyclerView.adapter as CategoryListAdapter).addItems(it) }
    }
}