package com.cuongngo.cinemax.services.remote

import com.cuongngo.cinemax.services.MediaApi
import com.cuongngo.cinemax.services.network.BaseRemoteDataSource

class MediaRemoteDataSource(private val apiService: MediaApi) : BaseRemoteDataSource() {
    //get detail
    suspend fun getMovieDetail(
        movie_id: String?
    ) = getResult { apiService.getMovieDetail(movie_id) }

    suspend fun getTvDetail(
        tv_id: String?
    ) = getResult { apiService.getTvDetail(tv_id) }

    //get upcoming
    suspend fun getUpcoming() = getResult { apiService.getUpcoming() }

    suspend fun getGenresMovie() = getResult { apiService.getGenresMovie() }

    suspend fun getGenresTV() = getResult { apiService.getGenresTV() }

    suspend fun getPopularMovie(
        page: Int?
    ) = getResult { apiService.getPopularMovie(page) }

    suspend fun searchMedia(
        query: String?,
        page: Int?
    ) = getResult { apiService.searchMedia(query, page) }
}