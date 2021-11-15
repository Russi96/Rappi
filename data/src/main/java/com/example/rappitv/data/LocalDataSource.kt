package com.example.rappitv.data

import com.example.rappitv.data.database.MoviesDao
import com.example.rappitv.data.database.entities.MoviesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private var moviesDao: MoviesDao
) {
    suspend fun insertMovies(moviesEntity: MoviesEntity) = moviesDao.insertMovies(moviesEntity)

    fun readMovies(): Flow<List<MoviesEntity>> {
        return moviesDao.readMovies()
    }

    fun searchMovies(searchQuery: String): Flow<List<MoviesEntity>> {
        return moviesDao.searchMoviesDatabase(searchQuery)
    }
}