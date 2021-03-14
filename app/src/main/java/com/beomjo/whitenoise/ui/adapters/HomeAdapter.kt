package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemHomeCategoryBinding
import com.beomjo.whitenoise.model.HomeCategory
import com.skydoves.bindables.BindingRecyclerViewAdapter

class HomeAdapter(
    private val clickListener: HomeItemViewHolder.OnClickListener
) : BindingRecyclerViewAdapter<HomeAdapter.HomeItemViewHolder>() {

    private val items = mutableListOf<HomeCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding =
            ItemHomeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<HomeCategory>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class HomeItemViewHolder(val binding: ItemHomeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        interface OnClickListener {
            fun onItemClick(view: View, item: HomeCategory)
        }

        fun bind(item: HomeCategory, clickListener: OnClickListener) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }
}