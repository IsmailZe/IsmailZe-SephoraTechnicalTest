package sephora.android.testtechnique.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import sc.android.whatishot.databinding.ItemBinding
import sephora.android.testtechnique.R
import sephora.android.testtechnique.data.model.Item

class ItemsAdapter :
    ListAdapter<Item, ItemsAdapter.ItemsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ItemsViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                descriptionItem.text = item.description
                Glide
                    .with(binding.root.context)
                    .load(item.image)
                    .transform(CenterCrop(), RoundedCorners(ROUNDING_RADIUS))
                    .placeholder(R.drawable.ic_track_placeholder)
                    .into(imageItem)
            }
        }
    }
    companion object {
        const val ROUNDING_RADIUS = 25
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item) =
        oldItem == newItem
}


