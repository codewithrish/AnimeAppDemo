package com.codewithrish.seekhoassignment.ui.anime_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.codewithrish.seekhoassignment.domain.intent.AnimeDetailIntent
import com.codewithrish.seekhoassignment.domain.state.AnimeDetailState
import com.codewithrish.seekhoassignment.ui.components.VideoPlayer
import com.codewithrish.seekhoassignment.viewmodel.AnimeDetailViewModel
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun AnimeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: AnimeDetailViewModel,
    id: Int,
    onBack: () -> Unit
) {
    LaunchedEffect(id) { viewModel.handle(AnimeDetailIntent.Load(id)) }
    val state by viewModel.state.collectAsState()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (state) {
            is AnimeDetailState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is AnimeDetailState.Success -> {
                val detail = (state as AnimeDetailState.Success).detail
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                        Color.Transparent
                                    )
                                )
                            )
                    ) {
                        if (detail.youtubeId?.isNotBlank() == true) {
                            VideoPlayer(
                                youtubeId = detail.youtubeId,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            AsyncImage(
                                model = detail.imageUrl,
                                contentDescription = detail.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = detail.title,
                        style = MaterialTheme.typography.headlineMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    GenreChipRow(genres = detail.genres)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Episodes: ${detail.episodes ?: "-"}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Rating: ${detail.score ?: "-"}", style = MaterialTheme.typography.bodyLarge)

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Plot",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = detail.synopsis ?: "No synopsis available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        onClick = onBack,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Back")
                    }
                }
            }
            is AnimeDetailState.Error -> {
                val msg = (state as AnimeDetailState.Error).message
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: $msg", color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.handle(AnimeDetailIntent.Refresh) }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GenreChipRow(genres: String) {
    val genreList = genres.split(",").map { it.trim() }
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        genreList.forEach { genre ->
            AssistChip(
                onClick = { /* Optionally handle genre click */ },
                label = { Text(text = genre) },
                shape = RoundedCornerShape(16.dp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    labelColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

