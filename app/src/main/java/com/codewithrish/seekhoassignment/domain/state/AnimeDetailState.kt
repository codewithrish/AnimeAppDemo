package com.codewithrish.seekhoassignment.domain.state

import com.codewithrish.seekhoassignment.data.local.entity.AnimeDetailEntity

sealed class AnimeDetailState {
    object Loading : AnimeDetailState()
    data class Success(val detail: AnimeDetailEntity) : AnimeDetailState()
    data class Error(val message: String) : AnimeDetailState()
}
