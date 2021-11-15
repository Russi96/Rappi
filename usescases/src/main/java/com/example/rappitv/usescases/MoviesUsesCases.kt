package com.example.rappitv.usescases

import com.example.rappitv.data.MoviesRepository

class MoviesUsesCases(private val moviesRepository: MoviesRepository) {

    suspend fun invoke(categoryId: String, watch: String, queries: HashMap<String, String>) =
        moviesRepository.getAllMoviesList(categoryId = categoryId, watch = watch, queries = queries)

    suspend fun invokeMovieDetails(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesRepository.getMovieDetails(movieId = movieId, watch = watch, queries = queries)

    suspend fun invokeMovieVideo(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesRepository.getMovieVideo(movieId = movieId, watch = watch, queries = queries)

    fun invokeRemoteMovies() = moviesRepository.remote

    fun searchMovies(searchQuery: String) =
        moviesRepository.remote.searchMovies(searchQuery = searchQuery)
}