package com.example.vismamusic.presentation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vismamusic.R
import com.example.vismamusic.databinding.ItemSongBinding
import com.example.vismamusic.domain.model.Song
import kotlin.math.min

class SongsAdapter(
    private val songs: List<Song>
) : RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {

    inner class SongsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(result: Song) {
            val binding = ItemSongBinding.bind(itemView)
            binding.title.text = result.title
            binding.size.text = result.size
            result.time.let {
                binding.time.text = it.toShow
            }

            Glide.with(binding.root)
                .load(result.image)
                .into(binding.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = min(songs.size, 5)
}