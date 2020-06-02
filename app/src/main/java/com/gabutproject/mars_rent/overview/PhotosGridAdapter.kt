package com.gabutproject.mars_rent.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gabutproject.mars_rent.databinding.GridviewItemBinding
import com.gabutproject.mars_rent.network.MarsProperty

class PhotosGridAdapter(private val clickListener: MarsRealEstateListener) :
    ListAdapter<MarsProperty, PhotosGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarsPropertyViewHolder {
        val binding = GridviewItemBinding.inflate(LayoutInflater.from(parent.context))
        return MarsPropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)

        // set onclick listener to holder, an pass the data
        holder.itemView.setOnClickListener { clickListener.onClick(marsProperty) }
        holder.bind(marsProperty)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

    }

    class MarsPropertyViewHolder(private var binding: GridviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            marsProperty: MarsProperty
        ) {
            binding.property = marsProperty
            binding.executePendingBindings()
        }
    }

    class MarsRealEstateListener(val clickListener: (MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }
}