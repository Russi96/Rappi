package com.example.rappitv.usescases


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.rappitv.data.LocalDataSource
import com.example.rappitv.data.MoviesRepository
import com.example.rappitv.data.RemoteResponseMoviesList
import com.example.rappitv.data.database.MoviesDao
import com.example.rappitv.domain.BelongsToCollection
import com.example.rappitv.domain.MovieDetails

import com.example.rappitv.domain.Movies
import com.example.rappitv.domain.MoviesVIdeo
import com.example.rappitv.utils.NetworkResult
import com.nhaarman.mockitokotlin2.given
import io.mockk.coEvery
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MoviesUsesCasesTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteResponseMoviesList: RemoteResponseMoviesList

    @Mock
    private lateinit var moviesDao: MoviesDao




    private lateinit var moviesUsesCases: MoviesUsesCases

    @Before
    fun setUp() {
        val localDataSource = LocalDataSource(moviesDao)
        val moviesRepository = MoviesRepository(remoteResponseMoviesList, localDataSource)
        moviesUsesCases = MoviesUsesCases(moviesRepository)
    }


    @Test
    fun `get all movies or series return expected value`() {
        val response = spyk(MutableLiveData<NetworkResult<Movies>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResult = NetworkResult.Success(mockMovies)
        coEvery {
            given(
                moviesUsesCases.invoke(
                    categoryId = "",
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResult))

            response.value = NetworkResult()

            Mockito.`when`(
                remoteResponseMoviesList.getAllMovies(
                    categoryId = "",
                    watch = "",
                    queries = queries
                )
            ).then {
                response
            }

        }

        Assert.assertNotNull(response)

    }

    @Test
    fun `get details movies or series return expected value`() {
        val response = spyk(MutableLiveData<NetworkResult<MovieDetails>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResult = NetworkResult.Success(mockDetailMovies)
        coEvery {
            given(
                moviesUsesCases.invokeMovieDetails(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResult))

            response.value = NetworkResult()

            Mockito.`when`(
                moviesUsesCases.invokeMovieDetails(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).then {
                response
            }

        }

        Assert.assertNotNull(response)

    }


    @Test
    fun `get videos movies or series return expected value`() {
        val response = spyk(MutableLiveData<NetworkResult<MoviesVIdeo>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResult = NetworkResult.Success(mockedVideo)
        coEvery {
            given(
                moviesUsesCases.invokeMovieVideo(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResult))

            response.value = NetworkResult()

            Mockito.`when`(
                moviesUsesCases.invokeMovieVideo(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).then {
                response
            }

        }

        Assert.assertNotNull(response)

    }


    private val mockMovies = Movies(page = 1, results = emptyList(), totalPages = 10, totalResults = 0)

    private val mockBelongsToCollection =
        BelongsToCollection(backdropPath = "", id = 0, name = "", posterPath = "")

    private val mockDetailMovies = MovieDetails(
        adult = true,
        backdropPath = "",
        belongsToCollection = mockBelongsToCollection,
        budget = 0,
        genres = emptyList(),
        homepage = "",
        id = 0,
        imdbId = "",
        originalLanguage = "",
        originalTitle = "",
        overview = "",
        popularity = 0.0,
        posterPath = "",
        productionCompanies = emptyList(),
        productionCountries = emptyList(),
        releaseDate = "",
        revenue = 0,
        runtime = 0,
        spokenLanguages = emptyList(),
        status = "",
        tagline = "",
        title = "",
        video = true, voteAverage = 0.0, voteCount = 0, name = "", first_air_date = ""
    )

    val mockedVideo = MoviesVIdeo(id = 0, results = emptyList())
}