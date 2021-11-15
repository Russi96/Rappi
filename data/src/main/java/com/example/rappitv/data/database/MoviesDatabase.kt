package com.example.rappitv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rappitv.data.database.entities.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
@TypeConverters(MoviesTypeConvert::class)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun moviesDao() : MoviesDao
}