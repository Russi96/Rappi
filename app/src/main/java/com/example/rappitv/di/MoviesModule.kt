package com.example.rappitv.di

import com.example.rappitv.data.LocalDataSource
import com.example.rappitv.data.MoviesRepository
import com.example.rappitv.usescases.MoviesUsesCases
import com.example.requestmanager.RemoteDataSource
import com.example.requestmanager.network.MoviesTvApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Singleton
    @Provides
    fun moviesRepository(
        remoteResponseMoviesList: RemoteDataSource,
        localDataSource: LocalDataSource
    ) = MoviesRepository(
        remoteResponseMoviesList = remoteResponseMoviesList,
        localDataSource = localDataSource
    )


    @Singleton
    @Provides
    fun remoteMoviesDataSource(moviesTvApi: MoviesTvApi) =
        RemoteDataSource(moviesTvApi = moviesTvApi)

    @Singleton
    @Provides
    fun moviesUsesCases(moviesRepository: MoviesRepository) =
        MoviesUsesCases(moviesRepository = moviesRepository)
}