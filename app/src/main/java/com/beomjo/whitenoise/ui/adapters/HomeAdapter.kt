package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemCategoryBinding
import com.beomjo.whitenoise.model.Category
import com.skydoves.bindables.BindingRecyclerViewAdapter

class HomeAdapter(
    private val clickListener: HomeCategoryItemViewHolder.OnClickListener
) : BindingRecyclerViewAdapter<HomeAdapter.HomeCategoryItemViewHolder>() {

    private val items = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCategoryItemViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeCategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCategoryItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<Category>) {
        val diffCallback = HomeCategoryDiffUtilCallback(this.items, items)
        val result = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        result.dispatchUpdatesTo(this)
    }

    class HomeCategoryItemViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        interface OnClickListener {
            fun onItemClick(view: View, item: Category)
        }

        fun bind(homeCategory: Category, listener: OnClickListener) {
            with(binding) {
                item = homeCategory
                clickListener = listener
                ViewCompat.setTransitionName(homeContainer, homeCategory.id.toString())
            }
        }
    }

    class HomeCategoryDiffUtilCallback(
        private val old: List<Category>,
        private val new: List<Category>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].id == new[newItemPosition].id
        }
    }
}