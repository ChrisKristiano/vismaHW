package com.example.vismamusic.presentation.details.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vismamusic.R
import com.example.vismamusic.databinding.ItemSongDetailsBinding
import com.example.vismamusic.domain.model.Song

class DetailsAdapter(
    private val songs: List<Song>
) : RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>() {

    inner class DetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(result: Song) {
            val binding = ItemSongDetailsBinding.bind(itemView)

            binding.title.text = result.title
            binding.size.text = result.size
            result.time.let {
                binding.time.text = it.toShow
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_details, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size
}