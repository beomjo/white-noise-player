package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemCategoryBinding
import com.beomjo.whitenoise.model.Category
import com.skydoves.bindables.BindingRecyclerViewAdapter

class CategoryListAdapter(
    private val clickListener: CategoryItemViewHolder.OnClickListener
) :
    BindingRecyclerViewAdapter<CategoryListAdapter.CategoryItemViewHolder>() {

    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val binding =
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size


    fun addItems(items: List<Category>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class CategoryItemViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        interface OnClickListener {
            fun onItemClick(view: View, item: Category)
        }

        fun bind(item: Category, clickListener: OnClickListener) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }


}