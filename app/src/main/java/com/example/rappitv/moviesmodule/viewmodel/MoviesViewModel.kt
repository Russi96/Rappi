package com.example.rappitv.moviesmodule.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.rappitv.data.database.entities.MoviesEntity

import com.example.rappitv.moviesmodule.model.MoviesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesInteractor: MoviesInteractor,
    application: Application
) : AndroidViewModel(application) {

    /** Room */

    val readMovies = moviesInteractor.readMovies().asLiveData()

    fun insertMovies(moviesEntity: MoviesEntity) = viewModelScope.launch(Dispatchers.IO) {
        moviesInteractor.insertMovies(moviesEntity)
    }

    fun searchMovies(searchQuery: String): LiveData<List<MoviesEntity>> {
        return moviesInteractor.searchMovies(searchQuery).asLiveData()
    }


    /** Retrofit */

    suspend fun getAllMovies(categoryId: String, watch: String, queries: HashMap<String, String>) =
        moviesInteractor.getAllMovies(categoryId = categoryId, watch = watch, queries = queries)

    suspend fun getDetailsMovies(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesInteractor.getMovieDetails(movieId = movieId, watch = watch, queries = queries)

    suspend fun getMovieVideo(movieId: Int, watch: String, queries: HashMap<String, String>) =
        moviesInteractor.getMovieVideo(movieId = movieId, watch = watch, queries = queries)


}