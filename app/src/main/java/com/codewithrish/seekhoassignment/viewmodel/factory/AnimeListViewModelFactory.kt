package com.codewithrish.seekhoassignment.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.viewmodel.AnimeListViewModel

class AnimeListViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimeListViewModel::class.java)) {
            return AnimeListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
