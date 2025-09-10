package com.codewithrish.seekhoassignment.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_detail")
data class AnimeDetailEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Float?,
    val genres: String, // comma separated
    val imageUrl: String,
    val youtubeId: String?,
    val trailerUrl: String?,
//    val mainCast: String // comma separated
)
