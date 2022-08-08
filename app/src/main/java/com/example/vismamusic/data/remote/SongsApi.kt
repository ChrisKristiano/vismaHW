package com.example.vismamusic.data.remote

import com.example.vismamusic.data.remote.dto.SongsDto
import retrofit2.http.GET

interface SongsApi {

    @GET("songs")
    suspend fun getSongs(): List<SongsDto>
}