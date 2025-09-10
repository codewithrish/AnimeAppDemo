package com.codewithrish.seekhoassignment.data.remote

import com.codewithrish.seekhoassignment.data.model.AnimeDetailResponse
import com.codewithrish.seekhoassignment.data.model.TopAnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApiService {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse

    @GET("anime/{anime_id}")
    suspend fun getAnimeDetails(@Path("anime_id") animeId: Int): AnimeDetailResponse
}
