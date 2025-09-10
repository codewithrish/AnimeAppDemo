package com.codewithrish.seekhoassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.codewithrish.seekhoassignment.data.local.AnimeDatabase
import com.codewithrish.seekhoassignment.data.remote.JikanApiService
import com.codewithrish.seekhoassignment.data.repository.AnimeRepository
import com.codewithrish.seekhoassignment.ui.MainScreen
import com.codewithrish.seekhoassignment.ui.anime_detail.AnimeDetailScreen
import com.codewithrish.seekhoassignment.ui.anime_list.AnimeListScreen
import com.codewithrish.seekhoassignment.ui.theme.SeekhoAssignmentTheme
import com.codewithrish.seekhoassignment.viewmodel.AnimeDetailViewModel
import com.codewithrish.seekhoassignment.viewmodel.AnimeListViewModel
import com.codewithrish.seekhoassignment.viewmodel.factory.AnimeDetailViewModelFactory
import com.codewithrish.seekhoassignment.viewmodel.factory.AnimeListViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, AnimeDatabase::class.java, "anime-db").build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(JikanApiService::class.java)
        val repo = AnimeRepository(api, db)

        setContent {
            SeekhoAssignmentTheme {
                MainScreen(repo)
            }
        }
    }
}
