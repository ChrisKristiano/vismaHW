package com.example.vismamusic.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vismamusic.common.RepoResult
import com.example.vismamusic.domain.model.CategorisedSongs
import com.example.vismamusic.domain.model.common.toTime
import com.example.vismamusic.domain.use_case.songs.GetCategorisedSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategorisedSongsUseCase: GetCategorisedSongsUseCase
) : ViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> = _state

    fun getSongs() {
        getCategorisedSongsUseCase().onEach { result ->
            when (result) {
                is RepoResult.Success -> handleSuccess(result.data)
                is RepoResult.Error -> _state.postValue(HomeState(error = result.message ?: "Something went wrong"))
                is RepoResult.Loading -> _state.postValue(HomeState(isLoading = true))
            }
        }.launchIn(viewModelScope)
    }

    private fun handleSuccess(data: List<CategorisedSongs>?) {
        data?.let { songs ->
            var totalSecondsMemory = 0
            var totalSecondsFilesystem = 0
            songs.onEach {
                totalSecondsMemory += it.songs.filter { song -> song.isStorageMemorySaved }
                    .sumOf { savedSong -> savedSong.time.totalSeconds }
                totalSecondsFilesystem += it.songs.filter { song -> song.isStorageFileSystemSaved }
                    .sumOf { savedSong -> savedSong.time.totalSeconds }
            }
            _state.postValue(
                HomeState(
                    songs = songs,
                    memoryLength = totalSecondsMemory.toTime(),
                    fileSystemLength = totalSecondsFilesystem.toTime()
                ))
        }
    }
}