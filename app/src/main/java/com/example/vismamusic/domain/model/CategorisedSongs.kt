package com.example.vismamusic.domain.model

data class CategorisedSongs(
    val category: String,
    val songs: List<Song>,
)
