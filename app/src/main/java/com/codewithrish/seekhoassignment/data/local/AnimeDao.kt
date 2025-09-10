package com.codewithrish.seekhoassignment.data.local

import androidx.room.*
import com.codewithrish.seekhoassignment.data.local.entity.AnimeDetailEntity
import com.codewithrish.seekhoassignment.data.local.entity.AnimeEntity

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)

    @Query("SELECT * FROM anime_detail WHERE id = :id LIMIT 1")
    suspend fun getAnimeDetail(id: Int): AnimeDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeDetail(detail: AnimeDetailEntity)
}
