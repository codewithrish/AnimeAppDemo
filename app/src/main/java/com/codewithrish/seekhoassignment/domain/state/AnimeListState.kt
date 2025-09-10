package com.codewithrish.seekhoassignment.domain.state

import com.codewithrish.seekhoassignment.data.local.entity.AnimeEntity

sealed class AnimeListState {
    object Loading : AnimeListState()
    data class Success(val animeList: List<AnimeEntity>) : AnimeListState()
    data class Error(val message: String) : AnimeListState()
}
