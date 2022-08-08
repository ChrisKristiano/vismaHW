package com.example.vismamusic.domain.repository

import com.example.vismamusic.data.remote.dto.SongsDto

interface SongsRepository {

    suspend fun getSongs(): List<SongsDto>
}