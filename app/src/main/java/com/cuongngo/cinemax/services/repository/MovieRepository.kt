package com.cuongngo.cinemax.services.repository

import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
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
    suspend fun getGenresTV(): BaseResult<GenresMovieResponse> {
        return movieRemoteDataSource.getGenresTV()
    }

    suspend fun getPopularMovie(
        page: Int?
    ): BaseResult<MovieResponse> {
        return movieRemoteDataSource.getPopularMovie(page)
    }

    suspend fun searchMedia(
        query: String?,
        page: Int?
    ): BaseResult<MultiMediaResponse> {
        return movieRemoteDataSource.searchMedia(query, page)
    }

}