package com.pegbeer.postsapp.ui.adapter

import com.pegbeer.postsapp.data.model.Photo
import com.pegbeer.postsapp.databinding.AlbumItemBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Photo>() {

        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            AlbumItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList.get(position))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Photo>) {
        differ.submitList(list)
    }

    inner class ViewHolder (private val binding:AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo) = with(binding) {
            Glide.with(binding.root)
                .load(item.url)
                .fitCenter()
                .into(albumImageView)
        }
    }
}