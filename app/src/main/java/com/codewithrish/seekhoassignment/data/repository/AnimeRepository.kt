package com.codewithrish.seekhoassignment.data.repository

import com.codewithrish.seekhoassignment.data.local.AnimeDatabase
import com.codewithrish.seekhoassignment.data.local.entity.AnimeDetailEntity
import com.codewithrish.seekhoassignment.data.local.entity.AnimeEntity
import com.codewithrish.seekhoassignment.data.remote.JikanApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository(
    private val api: JikanApiService,
    private val db: AnimeDatabase
) {
    suspend fun fetchTopAnime(forceRefresh: Boolean): List<AnimeEntity> = withContext(Dispatchers.IO) {
        val dao = db.animeDao()
        if (!forceRefresh) {
            val cached = dao.getAllAnime()
            if (cached.isNotEmpty()) return@withContext cached
        }
        val response = api.getTopAnime()
        val entities = response.data.map { anime ->
            AnimeEntity(id = anime.mal_id, title = anime.title, episodes = anime.episodes,
                score = anime.score, imageUrl = anime.images.jpg.image_url)
        }
        dao.insertAll(entities)
        entities
    }

    suspend fun fetchAnimeDetail(id: Int, forceRefresh: Boolean): AnimeDetailEntity = withContext(Dispatchers.IO) {
        val dao = db.animeDao()
        if (!forceRefresh) {
            dao.getAnimeDetail(id)?.let { return@withContext it }
        }
        val response = api.getAnimeDetails(id)
        val detail = response.data
        val entity = AnimeDetailEntity(
            id = detail.mal_id,
            title = detail.title,
            synopsis = detail.synopsis,
            episodes = detail.episodes,
            score = detail.score,
            genres = detail.genres.joinToString(",") { it.name },
            imageUrl = detail.images.jpg.image_url,
            youtubeId = detail.trailer?.youtube_id ?: "",
            trailerUrl = detail.trailer?.embed_url ?: "",
        )
        dao.insertAnimeDetail(entity)
        entity
    }
}
