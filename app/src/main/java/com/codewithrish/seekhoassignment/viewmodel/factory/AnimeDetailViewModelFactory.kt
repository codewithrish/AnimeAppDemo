package com.codewithrish.seekhoassignment.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.viewmodel.AnimeDetailViewModel

class AnimeDetailViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeDetailViewModel::class.java)) {
            return AnimeDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
