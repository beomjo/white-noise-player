package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemSoundBinding
import com.beomjo.whitenoise.model.Sound
import com.skydoves.bindables.BindingRecyclerViewAdapter

class SoundListAdapter(
    private val clickListener: SoundItemViewHolder.OnClickListener
) : BindingRecyclerViewAdapter<SoundListAdapter.SoundItemViewHolder>() {

    private val items = mutableListOf<Sound>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundItemViewHolder {
        val binding =
            ItemSoundBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SoundItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SoundItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<Sound>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class SoundItemViewHolder(val binding: ItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {
        interface OnClickListener {
            fun onItemClick(view: View, item: Sound)
        }

        fun bind(item: Sound, clickListener: OnClickListener) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }
}