package com.example.vismamusic.presentation.home

import com.example.vismamusic.domain.model.CategorisedSongs
import com.example.vismamusic.domain.model.common.Time

data class HomeState(
    val isLoading: Boolean = false,
    val songs: List<CategorisedSongs> = emptyList(),
    val fileSystemLength: Time = Time(0, 0, 0),
    val memoryLength: Time = Time(0, 0, 0),
    val error: String? = null
)