package com.aqua_waterfliter.rickmorty.homeScreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aqua_waterfliter.rickmorty.core.utils.CharPagingSource
import com.aqua_waterfliter.rickmorty.core.utils.Event
import com.aqua_waterfliter.rickmorty.core.utils.Logger
import com.aqua_waterfliter.rickmorty.core.utils.NetworkException
import com.aqua_waterfliter.rickmorty.core.utils.NetworkUnavailableException
import com.aqua_waterfliter.rickmorty.core.utils.createExceptionHandler
import com.aqua_waterfliter.rickmorty.homeScreen.domain.model.CharacterResults
import com.aqua_waterfliter.rickmorty.homeScreen.domain.usecase.GetCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacter,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadCharacters -> loadCharacters()
            is HomeEvent.LoadMoreCharacters -> loadMoreCharacters()

        }
    }

    val characters: Flow<PagingData<CharacterResults>> = Pager(
        config = PagingConfig(
            pageSize = 20, // Number of items per page
           // initialLoadSize = 20, // Ensures the initial load only loads one page
            prefetchDistance = 1, // Number of items to load ahead of the user's current scroll position
            enablePlaceholders = false
        ),
        pagingSourceFactory = { CharPagingSource(getCharacterUseCase) }
    ).flow
        .cachedIn(viewModelScope)

    private fun loadCharacters() {
        _state.update {
            it.copy(
                isLoading = true,
                character = null
            )
        }
        val errorMessage = "Failed To Load Characters"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            val response = getCharacterUseCase(page = 1)
            _state.update {
                it.copy(
                    isLoading = false,
                    character = response,
                    currentPage = 1,
                    endReached = response.info?.next == null
                )
            }
        }
    }

    private fun loadMoreCharacters() {
        if (_state.value.isPaging || _state.value.endReached) return

        _state.update { it.copy(isPaging = true) }
        val errorMessage = "Failed To Load More Characters"
        val exceptionHandler = viewModelScope.createExceptionHandler(errorMessage) { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            val nextPage = _state.value.currentPage + 1
            val response = getCharacterUseCase(page = nextPage)
            _state.update {
                it.copy(
                    isPaging = false,
                    character = it.character?.copy(
                        results = it.character.results + response.results // Append new results
                    ),
                    currentPage = nextPage,
                    endReached = response.info?.next == null // Update the endReached flag
                )
            }
        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException -> {
                Logger.i("Loading Characters Failed: ${failure.code} ")
                _state.update {
                    it.copy(isLoading = false, isPaging = false, failure = Event(failure))
                }
            }

            is NetworkUnavailableException -> {
                _state.update {
                    it.copy(isLoading = false, isPaging = false, failure = Event(failure))
                }
            }
        }
    }
}