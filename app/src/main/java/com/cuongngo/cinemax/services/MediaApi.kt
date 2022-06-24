package com.cuongngo.cinemax.services

import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.response.tv_response.TvDetailResponse
import com.cuongngo.cinemax.services.network.invoker.ApiClientFactory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MediaApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: String?
    ): Response<MovieDetailResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") movie_id: String?
    ): Response<TvDetailResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenresMovie(): Response<GenresMovieResponse>

    @GET("genre/tv/list")
    suspend fun getGenresTV(): Response<GenresMovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int? = null
    ): Response<MovieResponse>

    //search keyword
    @GET("search/multi")
    suspend fun searchMedia(
        @Query("query") query: String? = null,
        @Query("page") page: Int? = null
    ): Response<MultiMediaResponse>

    companion object {
        operator fun invoke(): MediaApi{
            return ApiClientFactory.createService()
        }
    }
}