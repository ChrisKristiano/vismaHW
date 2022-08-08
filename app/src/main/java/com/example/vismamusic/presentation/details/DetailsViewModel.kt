package com.example.vismamusic.presentation.details

import androidx.lifecycle.*
import com.example.vismamusic.common.RepoResult
import com.example.vismamusic.domain.use_case.songs.GetSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<DetailsState>()
    val state: LiveData<DetailsState> = _state

    init {
        savedStateHandle.get<String>("category")?.let { category ->
            getSongs(category)
        }
    }

    private fun getSongs(category: String) {
        getSongsUseCase(category).onEach { result ->
            when (result) {
                is RepoResult.Loading -> {
                    _state.postValue(DetailsState(isLoading = true))
                }
                is RepoResult.Error -> {
                    _state.postValue(DetailsState(error = result.message ?: "Something wrong"))
                }
                is RepoResult.Success -> {
                    _state.postValue(DetailsState(songs = result.data ?: emptyList(), categoryTitle = category))
                }
            }
        }.launchIn(viewModelScope)
    }
}