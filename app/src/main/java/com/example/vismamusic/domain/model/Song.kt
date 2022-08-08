package com.example.vismamusic.domain.model

import com.example.vismamusic.domain.model.common.Time

data class Song(
    val id: String,
    val title: String,
    val category: String,
    val size: String,
    val time: Time,
    val image: String,
    val isStorageMemorySaved: Boolean,
    val isStorageFileSystemSaved: Boolean
)
