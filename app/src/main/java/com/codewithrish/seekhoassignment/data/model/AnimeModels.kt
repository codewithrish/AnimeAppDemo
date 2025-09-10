package com.codewithrish.seekhoassignment.data.model

// Top anime list response
data class TopAnimeResponse(
    val data: List<AnimeSummary>
)

data class AnimeSummary(
    val mal_id: Int,
    val title: String,
    val episodes: Int?,
    val score: Float?,
    val images: Images
)

data class Images(
    val jpg: ImageUrl,
    val webp: ImageUrl? = null
)

data class ImageUrl(
    val image_url: String,
    val small_image_url: String? = null,
    val large_image_url: String? = null
)

// Anime detail response
data class AnimeDetailResponse(
    val data: AnimeDetail
)

data class AnimeDetail(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Float?,
    val genres: List<Genre>,
    val images: Images,
    val trailer: Trailer?,
    val producers: List<Producer>?,
    val licensors: List<Producer>?,
    val studios: List<Producer>?,
    val popularity: Int?,
    val rank: Int?,
    val members: Int?,
    val favorites: Int?
    // MAL JSON doesn’t include "cast" directly → keep it nullable or fetch from character endpoint
)

data class Genre(
    val name: String
)

data class Trailer(
    val youtube_id: String?,
    val url: String?,
    val embed_url: String?,
    val images: TrailerImages? = null
)

data class TrailerImages(
    val image_url: String?,
    val small_image_url: String?,
    val medium_image_url: String?,
    val large_image_url: String?,
    val maximum_image_url: String?
)

data class Producer(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
)

