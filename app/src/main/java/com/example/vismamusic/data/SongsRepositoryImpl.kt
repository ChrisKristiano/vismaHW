package com.example.vismamusic.data

import com.example.vismamusic.data.remote.SongsApi
import com.example.vismamusic.data.remote.dto.SongsDto
import com.example.vismamusic.domain.repository.SongsRepository
import javax.inject.Inject

class SongsRepositoryImpl @Inject constructor(
    private val api: SongsApi
) : SongsRepository {

    override suspend fun getSongs(): List<SongsDto> = api.getSongs()
}