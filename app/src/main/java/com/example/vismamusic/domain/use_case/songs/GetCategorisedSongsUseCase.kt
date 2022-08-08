package com.example.vismamusic.domain.use_case.songs

import com.example.vismamusic.common.RepoResult
import com.example.vismamusic.data.local.SharedPrefs
import com.example.vismamusic.data.local.TemporaryStorage
import com.example.vismamusic.data.remote.dto.SongsDto
import com.example.vismamusic.data.remote.dto.toSong
import com.example.vismamusic.domain.model.CategorisedSongs
import com.example.vismamusic.domain.repository.SongsRepository
import com.example.vismamusic.presentation.consts.StorageType.FILESYSTEM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetCategorisedSongsUseCase @Inject constructor(
    private val repository: SongsRepository,
    private val sharedPrefs: SharedPrefs,
    private val temporaryStorage: TemporaryStorage
) {
    operator fun invoke(): Flow<RepoResult<List<CategorisedSongs>>> = flow {
        try {
            emit(RepoResult.Loading<List<CategorisedSongs>>())
            val memoryIds = temporaryStorage.retrieveItemIds()
            val fileSystemIds = sharedPrefs.read(FILESYSTEM).toList()
            val songsResult = repository.getSongs().toCategorisedSongs(memoryIds, fileSystemIds)
            emit(RepoResult.Success<List<CategorisedSongs>>(songsResult))
        } catch (e: Exception) {
            emit(RepoResult.Error<List<CategorisedSongs>>(e.message.toString()))
        }
    }

    private fun List<SongsDto>.toCategorisedSongs(
        memoryIds: List<String>,
        fileSystemIds: List<String>
    ): List<CategorisedSongs> {
        val songsResult: MutableList<CategorisedSongs> = mutableListOf()
        this.groupBy { it.category }
            .onEach { entry ->
                songsResult.add(
                    CategorisedSongs(
                        entry.key,
                        entry.value.map { it.toSong(
                            memoryIds.contains(it.id),
                            fileSystemIds.contains(it.id)
                        ) }
                    )
                )
            }
        return songsResult
    }
}