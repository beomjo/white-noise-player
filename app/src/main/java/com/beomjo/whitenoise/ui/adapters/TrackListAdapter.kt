package com.beomjo.whitenoise.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.whitenoise.databinding.ItemTrackBinding
import com.beomjo.whitenoise.model.Track
import com.skydoves.bindables.BindingRecyclerViewAdapter

class TrackListAdapter(
    private val clickListener: TrackItemViewHolder.OnClickListener
) : BindingRecyclerViewAdapter<TrackListAdapter.TrackItemViewHolder>() {

    private val items = mutableListOf<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackItemViewHolder {
        val binding =
            ItemTrackBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return TrackItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackItemViewHolder, position: Int) {
        holder.bind(items[position], clickListener)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    fun addItems(items: List<Track>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class TrackItemViewHolder(val binding: ItemTrackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        interface OnClickListener {
            fun onItemClick(view: View, item: Track)
        }

        fun bind(item: Track, clickListener: OnClickListener) {
            binding.item = item
            binding.clickListener = clickListener
        }
    }
}