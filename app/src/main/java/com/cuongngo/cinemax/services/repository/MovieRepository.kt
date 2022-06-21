package com.cuongngo.cinemax.services.repository

import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.remote.MovieRemoteDataSource

class MovieRepository( private val movieRemoteDataSource: MovieRemoteDataSource) {
    suspend fun getMovieDetail(
        movie_id: String?
    ): BaseResult<MovieDetailResponse> {
        return movieRemoteDataSource.getMovieDetail(movie_id)
    }

    suspend fun getUpcoming (): BaseResult<MovieResponse> {
        return movieRemoteDataSource.getUpcoming()
    }
    suspend fun getGenresMovie(): BaseResult<GenresMovieResponse> {
        return movieRemoteDataSource.getGenresMovie()
    }

    suspend fun getPopularMovie(
        page: Int?
    ): BaseResult<MovieResponse> {
        return movieRemoteDataSource.getPopularMovie(page)
    }
}