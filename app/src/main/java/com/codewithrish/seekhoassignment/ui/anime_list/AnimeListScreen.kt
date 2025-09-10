package com.codewithrish.seekhoassignment.ui.anime_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codewithrish.seekhoassignment.domain.intent.AnimeListIntent
import com.codewithrish.seekhoassignment.domain.state.AnimeListState
import com.codewithrish.seekhoassignment.ui.components.AnimeListItem
import com.codewithrish.seekhoassignment.viewmodel.AnimeListViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel,
    onAnimeSelected: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    var animateAllItems by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.handle(AnimeListIntent.Load)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Popular Anime",
                        style = typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (state) {
                is AnimeListState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                is AnimeListState.Success -> {
                    val animeList = (state as AnimeListState.Success).animeList

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState,
//                        contentPadding = PaddingValues(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        itemsIndexed(animeList) { index, anime ->
                            val visible = animateAllItems || index < 5 // initially show first few immediately
                            LaunchedEffect(index) {
                                delay(50L * index)
                                animateAllItems = true
                            }
                            AnimatedVisibility(
                                visible = visible,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                AnimeListItem(anime = anime, onAnimeSelected = onAnimeSelected)
                            }
                        }
                    }
                }
                is AnimeListState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Oops! ${(state as AnimeListState.Error).message}",
                            style = typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 24.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Button(
                            onClick = { viewModel.handle(AnimeListIntent.Refresh) },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = "Try Again",
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
