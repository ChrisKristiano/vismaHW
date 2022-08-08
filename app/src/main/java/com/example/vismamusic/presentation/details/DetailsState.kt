package com.example.vismamusic.presentation.details

import com.example.vismamusic.domain.model.Song

data class DetailsState(
    val isLoading: Boolean = false,
    val songs: List<Song> = emptyList(),
    val categoryTitle: String = "",
    val error: String? = null
)