package com.example.requestmanager


import androidx.lifecycle.MutableLiveData
import com.example.rappitv.data.RemoteResponseMoviesList
import com.example.rappitv.domain.MovieDetails
import com.example.rappitv.domain.Movies
import com.example.rappitv.domain.MoviesVIdeo
import com.example.rappitv.utils.NetworkResult
import com.example.requestmanager.network.MoviesTvApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesTvApi: MoviesTvApi
) : RemoteResponseMoviesList {
    private var moviesListResponse: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()
    private var movieDetailsResponse: MutableLiveData<NetworkResult<MovieDetails>> =
        MutableLiveData()
    private var movieVideoResponse: MutableLiveData<NetworkResult<MoviesVIdeo>> = MutableLiveData()

    override suspend fun getAllMovies(
        categoryId: String,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<Movies>> {
        moviesListResponse.value = NetworkResult.Loading()
        val response =
            moviesTvApi.getAllMovies(categoryId = categoryId, queries = queries, watchParam = watch)

        when {
            response.message().toString().contains("timeout") -> {
                moviesListResponse.value = NetworkResult.Error(message = "Timeout")
            }
            response.code() == 403 -> {
                moviesListResponse.value = NetworkResult.Error(message = "API Key Limited.")
            }

            response.isSuccessful -> {
                val movies = response.body()
                movies?.let { moviesList ->
                    moviesListResponse.value = NetworkResult.Success(data = moviesList)

                }
            }
            else -> {
                moviesListResponse.value = NetworkResult.Error(message = response.message())
            }
        }

        return moviesListResponse
    }

    override suspend fun getMovieDetails(
        movieId: Int,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<MovieDetails>> {

        movieDetailsResponse.value = NetworkResult.Loading()
        val response =
            moviesTvApi.getMovieDetails(movieId = movieId, queries = queries, watchParam = watch)
        when {
            response.message().toString().contains("timeout") -> {
                movieDetailsResponse.value = NetworkResult.Error(message = "Timeout")
            }
            response.code() == 403 -> {
                movieDetailsResponse.value = NetworkResult.Error(message = "API Key Limited.")
            }

            response.isSuccessful -> {
                val movies = response.body()
                movies?.let { moviesList ->
                    movieDetailsResponse.value = NetworkResult.Success(data = moviesList)
                }
            }
            else -> {
                movieDetailsResponse.value = NetworkResult.Error(message = response.message())
            }
        }

        return movieDetailsResponse
    }

    override suspend fun getVideoMovies(
        movieId: Int,
        watch: String,
        queries: HashMap<String, String>
    ): MutableLiveData<NetworkResult<MoviesVIdeo>> {
        movieVideoResponse.value = NetworkResult.Loading()
        val response =
            moviesTvApi.getMovieVideo(movieId = movieId, queries = queries, watchParam = watch)
        when {
            response.message().toString().contains("timeout") -> {
                movieVideoResponse.value = NetworkResult.Error(message = "Timeout")
            }
            response.code() == 403 -> {
                movieVideoResponse.value = NetworkResult.Error(message = "API Key Limited.")
            }

            response.isSuccessful -> {
                val movieVideo = response.body()
                movieVideo?.let { moviesList ->
                    movieVideoResponse.value = NetworkResult.Success(data = moviesList)
                }
            }
            else -> {
                movieVideoResponse.value = NetworkResult.Error(message = response.message())
            }
        }

        return movieVideoResponse
    }

}