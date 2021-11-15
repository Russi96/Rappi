package com.example.rappitv.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
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
class RepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remoteResponseMoviesList: RemoteResponseMoviesList

    @Mock
    private lateinit var moviesDao: MoviesDao

    private lateinit var repositoryTest: MoviesRepository


    @Before
    fun setUp() {
        val localDataSource = LocalDataSource(moviesDao)
        repositoryTest = MoviesRepository(
            remoteResponseMoviesList = remoteResponseMoviesList, localDataSource
        )
    }


    @Test
    fun `get all movies and series for API`() {
        val dataResponse = spyk(MutableLiveData<NetworkResult<Movies>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResult = NetworkResult.Success(mockMovies)

        coEvery {

            given(
                remoteResponseMoviesList.getAllMovies(
                    categoryId = "",
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResult))

            dataResponse.value = NetworkResult()

            Mockito.`when`(
                remoteResponseMoviesList.getAllMovies(
                    categoryId = "",
                    watch = "",
                    queries = queries
                )
            ).then {
                dataResponse
            }
        }
        Assert.assertNotNull(dataResponse)
    }

    @Test
    fun `get movie and series details for API`() {
        val dataResponse = spyk(MutableLiveData<NetworkResult<MovieDetails>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResultDetails = NetworkResult.Success(mockDetailMovies)

        coEvery {

            given(
                remoteResponseMoviesList.getMovieDetails(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResultDetails))

            dataResponse.value = NetworkResult()

            Mockito.`when`(
                remoteResponseMoviesList.getMovieDetails(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).then {
                dataResponse
            }
        }

        Assert.assertNotNull(dataResponse)
    }


    @Test
    fun `get movie and series videos for API`() {
        val dataResponse = spyk(MutableLiveData<NetworkResult<MoviesVIdeo>>())
        val queries: HashMap<String, String> = HashMap()
        val expectedResultDetails = NetworkResult.Success(mockedVideo)

        coEvery {

            given(
                remoteResponseMoviesList.getVideoMovies(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).willReturn(MutableLiveData(expectedResultDetails))

            dataResponse.value = NetworkResult()

            Mockito.`when`(
                remoteResponseMoviesList.getVideoMovies(
                    movieId = 0,
                    watch = "",
                    queries = queries
                )
            ).then {
                dataResponse
            }
        }

        Assert.assertNotNull(dataResponse)
    }

}

val mockMovies = Movies(page = 1, results = emptyList(), totalPages = 10, totalResults = 0)

val mockBelongsToCollection =
    BelongsToCollection(backdropPath = "", id = 0, name = "", posterPath = "")

val mockDetailMovies = MovieDetails(
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

