package com.codewithrish.seekhoassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.domain.intent.AnimeListIntent
import com.codewithrish.seekhoassignment.domain.state.AnimeListState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeListViewModel(private val repo: AnimeRepository) : ViewModel() {
    private val _state = MutableStateFlow<AnimeListState>(AnimeListState.Loading)
    val state: StateFlow<AnimeListState> = _state

    fun handle(intent: AnimeListIntent) {
        when(intent) {
            AnimeListIntent.Load ->
                loadAnimeList(forceRefresh = false)
            AnimeListIntent.Refresh ->
                loadAnimeList(forceRefresh = true)
            is AnimeListIntent.Select -> {
                // Navigation handled in UI 
            }
        }
    }

    private fun loadAnimeList(forceRefresh: Boolean) {
        viewModelScope.launch {
            _state.value = AnimeListState.Loading
            try {
                val list = repo.fetchTopAnime(forceRefresh)
                _state.value = AnimeListState.Success(list)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = AnimeListState.Error(e.message ?: "Unknown error")
            }
        }
    }
}
