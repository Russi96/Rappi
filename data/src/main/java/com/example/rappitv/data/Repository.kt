package com.example.rappitv.data

import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteResponseMoviesList: RemoteResponseMoviesList,
    localDataSource: LocalDataSource
) {

    val remote = localDataSource

    suspend fun getAllMoviesList(
        categoryId: String,
        watch: String,
        queries: HashMap<String, String>
    ) =
        remoteResponseMoviesList.getAllMovies(
            categoryId = categoryId,
            watch = watch,
            queries = queries
        )

    suspend fun getMovieDetails(
        movieId: Int, watch: String,
        queries: HashMap<String, String>
    ) =
        remoteResponseMoviesList.getMovieDetails(
            movieId = movieId, watch = watch,
            queries = queries
        )

    suspend fun getMovieVideo(
        movieId: Int, watch: String,
        queries: HashMap<String, String>
    ) =
        remoteResponseMoviesList.getVideoMovies(
            movieId = movieId, watch = watch,
            queries = queries
        )
}