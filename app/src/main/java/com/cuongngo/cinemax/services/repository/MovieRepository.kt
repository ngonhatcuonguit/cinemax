package com.cuongngo.cinemax.services.repository

import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.remote.MovieRemoteDataSource

class MovieRepository( private val movieRemoteDataSource: MovieRemoteDataSource) {
    suspend fun getMovieDetail(
        movie_id: String?
    ): BaseResult<MovieDetailResponse> {
        return movieRemoteDataSource.getMovieDetail(movie_id)
    }
}