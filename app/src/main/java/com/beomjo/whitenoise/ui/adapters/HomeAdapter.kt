package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemHomeBinding
import com.beomjo.whitenoise.model.HomeItem
import com.skydoves.bindables.BindingRecyclerViewAdapter

class HomeAdapter(
    private val clickListener: HomeItemViewHolder.OnClickListener
) : BindingRecyclerViewAdapter<HomeAdapter.HomeItemViewHolder>() {

    private val items = mutableListOf<HomeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<HomeItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class HomeItemViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {

        interface OnClickListener {
            fun onItemClick(item: HomeItem)
        }

        fun bind(item: HomeItem, clickListener: OnClickListener) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }
}