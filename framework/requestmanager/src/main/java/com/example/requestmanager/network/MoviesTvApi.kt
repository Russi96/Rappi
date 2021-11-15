package com.example.requestmanager.network

import com.example.rappitv.domain.MovieDetails
import com.example.rappitv.domain.Movies
import com.example.rappitv.domain.MoviesVIdeo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MoviesTvApi {

    @GET("{watch}/{categoryId}")
    suspend fun getAllMovies(
        @Path("categoryId") categoryId: String,
        @Path("watch") watchParam: String,
        @QueryMap queries: Map<String, String>
    ): Response<Movies>

    @GET("{watch}/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Path("watch") watchParam: String,
        @QueryMap queries: Map<String, String>
    ): Response<MovieDetails>

    @GET("{watch}/{movieId}/videos")
    suspend fun getMovieVideo(
        @Path("movieId") movieId: Int,
        @Path("watch") watchParam: String,
        @QueryMap queries: Map<String, String>
    ): Response<MoviesVIdeo>
}