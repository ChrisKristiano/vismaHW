package com.example.vismamusic.domain.use_case.songs

import com.example.vismamusic.common.RepoResult
import com.example.vismamusic.data.local.SharedPrefs
import com.example.vismamusic.data.local.TemporaryStorage
import com.example.vismamusic.data.remote.dto.toSong
import com.example.vismamusic.domain.model.Song
import com.example.vismamusic.domain.repository.SongsRepository
import com.example.vismamusic.presentation.consts.StorageType.FILESYSTEM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetSongsUseCase @Inject constructor(
    private val repository: SongsRepository,
    private val sharedPrefs: SharedPrefs,
    private val temporaryStorage: TemporaryStorage
) {
    operator fun invoke(category: String? = null): Flow<RepoResult<List<Song>>> = flow {
        try {
            emit(RepoResult.Loading<List<Song>>())
            val memoryIds = temporaryStorage.retrieveItemIds()
            val fileSystemIds = sharedPrefs.read(FILESYSTEM)
            var songsResult = repository.getSongs().map { it.toSong(
                memoryIds.contains(it.id),
                fileSystemIds.contains(it.id)
            ) }
            category?.let {
                songsResult = songsResult.filter { it.category == category }
            }
            emit(RepoResult.Success<List<Song>>(songsResult))
        } catch (e: Exception) {
            emit(RepoResult.Error<List<Song>>(e.message.toString()))
        }
    }
}