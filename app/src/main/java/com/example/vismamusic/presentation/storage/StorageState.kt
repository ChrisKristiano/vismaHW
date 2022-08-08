package com.example.vismamusic.presentation.storage

import com.example.vismamusic.domain.model.Song

data class StorageState(
    val isLoading: Boolean = false,
    val songs: List<Song> = emptyList(),
    val storageType: String = "",
    val error: String? = null
)