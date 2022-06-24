package com.cuongngo.cinemax.services.remote

import com.cuongngo.cinemax.services.MovieApi
import com.cuongngo.cinemax.services.network.BaseRemoteDataSource

class MovieRemoteDataSource(private val apiService: MovieApi) : BaseRemoteDataSource() {
    //get detail
    suspend fun getMovieDetail(
        movie_id: String?
    ) = getResult { apiService.getMovieDetail(movie_id) }

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