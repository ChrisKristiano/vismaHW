package com.example.vismamusic.presentation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vismamusic.R
import com.example.vismamusic.databinding.ItemSongCategoryBinding
import com.example.vismamusic.domain.model.CategorisedSongs
import com.example.vismamusic.presentation.home.HomeFragmentDirections

class SongCategoriesAdapter(
    private val songCategories: List<CategorisedSongs>
) : RecyclerView.Adapter<SongCategoriesAdapter.SongCategoriesViewHolder>() {

    inner class SongCategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(result: CategorisedSongs) {
            val binding = ItemSongCategoryBinding.bind(itemView)
            binding.song.adapter = SongsAdapter(result.songs)
            binding.song.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
            binding.contentTitle.text = result.category
            binding.buttonSeeAll.setOnClickListener {
                it.findNavController().navigate(HomeFragmentDirections.actionHomeToDetails(result.category))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongCategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song_category, parent, false)
        return SongCategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongCategoriesViewHolder, position: Int) {
        holder.bind(songCategories[position])
    }

    override fun getItemCount(): Int = songCategories.size
}