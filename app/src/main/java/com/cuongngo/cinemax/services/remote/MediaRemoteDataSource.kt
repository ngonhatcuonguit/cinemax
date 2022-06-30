package com.cuongngo.cinemax.services.remote

import com.cuongngo.cinemax.services.MediaApi
import com.cuongngo.cinemax.services.network.BaseRemoteDataSource
import com.cuongngo.cinemax.utils.Constants

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

    suspend fun getTrending(
        media_type: String,
        time_window: String,
        page: Int,
    ) = getResult { apiService.getTrending(media_type, time_window, page) }

    suspend fun getTrendingMovie(
        media_type: String? = Constants.MediaType.MOVIE,
        time_window: String,
        page: Int,
    ) = getResult { apiService.getTrendingMovie(media_type, time_window, page) }

    suspend fun getPopularPersonal(
        page: Int,
    ) = getResult { apiService.getPopularPersonal(page) }

    suspend fun getListVideo(
        movie_id: String,
    ) = getResult { apiService.getListVideos(movie_id) }
}