package com.codewithrish.seekhoassignment.domain.intent

sealed class AnimeDetailIntent {
    data class Load(val id: Int) : AnimeDetailIntent()
    object Refresh : AnimeDetailIntent()
}
