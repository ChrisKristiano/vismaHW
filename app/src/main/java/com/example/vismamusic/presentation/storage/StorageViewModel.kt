package com.example.vismamusic.presentation.storage

import androidx.lifecycle.*
import com.example.vismamusic.common.RepoResult
import com.example.vismamusic.domain.use_case.songs.GetSongsUseCase
import com.example.vismamusic.domain.use_case.storage.file_system.PostFilesystemStorageUseCase
import com.example.vismamusic.domain.use_case.storage.temporary.PostMemoryStorageUseCase
import com.example.vismamusic.presentation.consts.StorageType.FILESYSTEM
import com.example.vismamusic.presentation.consts.StorageType.MEMORY
import com.example.vismamusic.presentation.consts.StorageType.PARAM_STORAGE_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val postFilesystemStorageUseCase: PostFilesystemStorageUseCase,
    private val postMemoryStorageUseCase: PostMemoryStorageUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<StorageState>()
    val state: LiveData<StorageState> = _state

    private lateinit var storageType: String

    init {
        savedStateHandle.get<String>(PARAM_STORAGE_TYPE)?.let { storageType ->
            this.storageType = storageType
            getSongs(storageType)
        }
    }

    fun saveSong(id: String) {
        if (storageType == MEMORY) {
            postMemoryStorageUseCase(id)
        } else if (storageType == FILESYSTEM) {
            postFilesystemStorageUseCase(id)
        }
    }

    private fun getSongs(storageType: String) {
        getSongsUseCase().onEach { result ->
            when (result) {
                is RepoResult.Loading -> {
                    _state.postValue(StorageState(isLoading = true))
                }
                is RepoResult.Error -> {
                    _state.postValue(StorageState(error = result.message ?: ""))
                }
                is RepoResult.Success -> {
                    _state.postValue(StorageState(songs = result.data ?: emptyList(), storageType = storageType))
                }
            }
        }.launchIn(viewModelScope)
    }
}