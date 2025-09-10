package com.codewithrish.seekhoassignment.domain.intent

sealed class AnimeListIntent {
    object Load : AnimeListIntent()
    object Refresh : AnimeListIntent()
    data class Select(val id: Int) : AnimeListIntent()
}
