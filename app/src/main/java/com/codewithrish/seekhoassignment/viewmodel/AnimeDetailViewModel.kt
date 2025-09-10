package com.codewithrish.seekhoassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.domain.intent.AnimeDetailIntent
import com.codewithrish.seekhoassignment.domain.state.AnimeDetailState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repo: AnimeRepository) : ViewModel() {
    private val _state = MutableStateFlow<AnimeDetailState>(AnimeDetailState.Loading)
    val state: StateFlow<AnimeDetailState> = _state

    fun handle(intent: AnimeDetailIntent) {
        when(intent) {
            is AnimeDetailIntent.Load ->
                loadAnimeDetail(intent.id, forceRefresh = false)
            AnimeDetailIntent.Refresh ->
                _state.value.let {
                    if (it is AnimeDetailState.Success) {
                        loadAnimeDetail(it.detail.id, forceRefresh = true)
                    }
                }
        }
    }

    private fun loadAnimeDetail(id: Int, forceRefresh: Boolean) {
        viewModelScope.launch {
            _state.value = AnimeDetailState.Loading
            try {
                val detail = repo.fetchAnimeDetail(id, forceRefresh)
                _state.value = AnimeDetailState.Success(detail)
            } catch (e: Exception) {
                _state.value = AnimeDetailState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
