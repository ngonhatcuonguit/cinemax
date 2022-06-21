package com.cuongngo.cinemax.services

import com.cuongngo.cinemax.response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.services.network.invoker.ApiClientFactory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: String?
    ): Response<MovieDetailResponse>

    @GET("movie/upcoming")
    suspend fun getUpcoming(): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenresMovie(): Response<GenresMovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("page") page: Int? = null
    ): Response<MovieResponse>

    companion object {
        operator fun invoke(): MovieApi{
            return ApiClientFactory.createService()
        }
    }
}