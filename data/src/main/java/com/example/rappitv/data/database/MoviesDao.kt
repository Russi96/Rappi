package com.example.rappitv.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rappitv.data.database.entities.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesEntity: MoviesEntity)

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun readMovies() : Flow<List<MoviesEntity>>

    @Query("SELECT * FROM movies_table WHERE name LIKE :searchQuery")
    fun searchMoviesDatabase(searchQuery: String) : Flow<List<MoviesEntity>>
}