package com.codewithrish.seekhoassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codewithrish.seekhoassignment.data.local.entity.AnimeDetailEntity
import com.codewithrish.seekhoassignment.data.local.entity.AnimeEntity

@Database(entities = [AnimeEntity::class, AnimeDetailEntity::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}
