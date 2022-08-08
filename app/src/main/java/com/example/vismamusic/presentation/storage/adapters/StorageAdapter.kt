package com.example.vismamusic.presentation.storage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.vismamusic.R
import com.example.vismamusic.databinding.ItemStorageDetailBinding
import com.example.vismamusic.domain.model.Song
import com.example.vismamusic.presentation.consts.StorageType.MEMORY

class StorageAdapter(
    private val songs: List<Song>,
    private val storageType: String? = null,
    private val funSaveSong: (String) -> Unit
) : RecyclerView.Adapter<StorageAdapter.StorageViewHolder>() {

    inner class StorageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(result: Song) {
            val binding = ItemStorageDetailBinding.bind(itemView)

            binding.title.text = result.title
            binding.size.text = result.size
            result.time.let {
                binding.time.text = it.toShow
            }

            if (storageType == MEMORY) {
                handleIcon(result.isStorageMemorySaved, binding)
            } else {
                handleIcon(result.isStorageFileSystemSaved, binding)
            }

            val imageView = binding.image
            imageView.setOnClickListener {
                funSaveSong(result.id)
                imageView.setImageDrawable(AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_baseline_check_24))
            }
        }

        private fun handleIcon(isSaved: Boolean, binding: ItemStorageDetailBinding) {
            if (isSaved) {
                binding.image.setImageDrawable(AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_baseline_check_24))
            } else {
                binding.image.setImageDrawable(AppCompatResources.getDrawable(binding.root.context, R.drawable.ic_baseline_save_24))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_storage_detail, parent, false)
        return StorageViewHolder(view)
    }

    override fun onBindViewHolder(holder: StorageViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size
}