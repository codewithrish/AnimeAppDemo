package com.codewithrish.seekhoassignment.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.ui.anime_detail.AnimeDetailScreen
import com.codewithrish.seekhoassignment.ui.anime_list.AnimeListScreen
import com.codewithrish.seekhoassignment.viewmodel.AnimeDetailViewModel
import com.codewithrish.seekhoassignment.viewmodel.AnimeListViewModel
import com.codewithrish.seekhoassignment.viewmodel.factory.AnimeDetailViewModelFactory
import com.codewithrish.seekhoassignment.viewmodel.factory.AnimeListViewModelFactory

@Composable
fun MainScreen(animeRepo: AnimeRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list",
//        modifier = Modifier.padding(innerPadding)  // apply scaffold padding here
    ) {
        composable("list") {
            val listVM: AnimeListViewModel = viewModel(
                factory = AnimeListViewModelFactory(animeRepo)
            )
            AnimeListScreen(listVM) { selectedId ->
                navController.navigate("detail/$selectedId")
            }
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: return@composable
            val detailVM: AnimeDetailViewModel = viewModel(
                factory = AnimeDetailViewModelFactory(animeRepo)
            )
            Scaffold { innerPadding ->
                AnimeDetailScreen(modifier = Modifier.padding(innerPadding), detailVM, id) {
                    navController.popBackStack()
                }
            }
        }
    }
}
