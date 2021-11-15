package com.example.rappitv.moviesmodule.model

import com.example.rappitv.data.database.entities.MoviesEntity
import com.example.rappitv.usescases.MoviesUsesCases
import javax.inject.Inject

class MoviesInteractor @Inject constructor(
    private val moviesUsesCases: MoviesUsesCases

) {
    suspend fun getAllMovies(categoryId: String, watch: String, queries: HashMap<String, String>) =
        moviesUsesCases.invoke(categoryId = categoryId, watch = watch, queries = queries)

    suspend fun getMovieDetails(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesUsesCases.invokeMovieDetails(movieId = movieId,  watch = watch, queries = queries)

    suspend fun getMovieVideo(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesUsesCases.invokeMovieVideo(movieId = movieId, watch = watch, queries = queries)

    suspend fun insertMovies(moviesEntity: MoviesEntity) =
        moviesUsesCases.invokeRemoteMovies().insertMovies(moviesEntity = moviesEntity)

    fun readMovies() = moviesUsesCases.invokeRemoteMovies().readMovies()

    fun searchMovies(searchQuery: String) = moviesUsesCases.searchMovies(searchQuery = searchQuery)
}