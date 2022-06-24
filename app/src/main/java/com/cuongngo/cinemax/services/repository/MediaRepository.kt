package com.cuongngo.cinemax.services.repository

import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.response.tv_response.TvDetailResponse
import com.cuongngo.cinemax.services.network.BaseResult
import com.cuongngo.cinemax.services.remote.MediaRemoteDataSource

class MediaRepository(private val mediaRemoteDataSource: MediaRemoteDataSource) {
    suspend fun getMovieDetail(
        movie_id: String?
    ): BaseResult<MovieDetailResponse> {
        return mediaRemoteDataSource.getMovieDetail(movie_id)
    }

    suspend fun getTvDetail(
        tv_id: String?
    ): BaseResult<TvDetailResponse> {
        return mediaRemoteDataSource.getTvDetail(tv_id)
    }

    suspend fun getUpcoming (): BaseResult<MovieResponse> {
        return mediaRemoteDataSource.getUpcoming()
    }
    suspend fun getGenresMovie(): BaseResult<GenresMovieResponse> {
        return mediaRemoteDataSource.getGenresMovie()
    }
    suspend fun getGenresTV(): BaseResult<GenresMovieResponse> {
        return mediaRemoteDataSource.getGenresTV()
    }

    suspend fun getPopularMovie(
        page: Int?
    ): BaseResult<MovieResponse> {
        return mediaRemoteDataSource.getPopularMovie(page)
    }

    suspend fun searchMedia(
        query: String?,
        page: Int?
    ): BaseResult<MultiMediaResponse> {
        return mediaRemoteDataSource.searchMedia(query, page)
    }

}