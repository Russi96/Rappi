package com.example.rappitv.data

import androidx.lifecycle.MutableLiveData
import com.example.rappitv.domain.MovieDetails
import com.example.rappitv.domain.Movies
import com.example.rappitv.domain.MoviesVIdeo
import com.example.rappitv.utils.NetworkResult

interface RemoteResponseMoviesList {

    suspend fun getAllMovies(
        categoryId: String,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<Movies>>

    suspend fun getMovieDetails(
        movieId: Int,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<MovieDetails>>

    suspend fun getVideoMovies(
        movieId: Int,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<MoviesVIdeo>>
}

