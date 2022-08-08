package com.example.vismamusic.data.remote.dto

import com.example.vismamusic.domain.model.Song
import com.example.vismamusic.domain.model.common.toTime
import com.google.gson.annotations.SerializedName

data class SongsDto(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val category: String,
    val size: String,
    val time: Int,
    val image: String
)

fun SongsDto.toSong(isStorageMemorySaved: Boolean = false,
                     isStorageFileSystemSaved: Boolean = false): Song {
    return Song(
        id = id,
        title = title,
        category = category,
        size = size,
        time = time.toTime(),
        image = image,
        isStorageMemorySaved = isStorageMemorySaved,
        isStorageFileSystemSaved = isStorageFileSystemSaved,
    )
}