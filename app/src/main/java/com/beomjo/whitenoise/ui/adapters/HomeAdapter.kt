package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
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
        val diffCallback = HomeCategoryDiffUtilCallback(this.items, items)
        val result = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        result.dispatchUpdatesTo(this)
    }

    class HomeItemViewHolder(val binding: ItemHomeCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        interface OnClickListener {
            fun onItemClick(view: View, item: HomeCategory)
        }

        fun bind(homeCategory: HomeCategory, listener: OnClickListener) {
            with(binding) {
                item = homeCategory
                clickListener = listener
                ViewCompat.setTransitionName(homeContainer, homeCategory.id.toString())
            }
        }
    }

    class HomeCategoryDiffUtilCallback(
        private val old: List<HomeCategory>,
        private val new: List<HomeCategory>
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