package com.cuongngo.cinemax.services

import com.cuongngo.cinemax.response.movie_response.GenresMovieResponse
import com.cuongngo.cinemax.response.MovieDetailResponse
import com.cuongngo.cinemax.response.MovieResponse
import com.cuongngo.cinemax.response.MultiMediaResponse
import com.cuongngo.cinemax.response.PersonalResponse
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

    //get trending
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrending(
        @Path("media_type") media_type: String?,
        @Path("time_window") time_window: String?,
        @Query("page") page: Int? = null
    ): Response<MultiMediaResponse>

    //get trending movie
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovie(
        @Path("media_type") media_type: String?,
        @Path("time_window") time_window: String?,
        @Query("page") page: Int? = null
    ): Response<MovieResponse>

    //get popular personal
    @GET("person/popular")
    suspend fun getPopularPersonal(
        @Query("page") page: Int? = null
    ): Response<PersonalResponse>

    companion object {
        operator fun invoke(): MediaApi{
            return ApiClientFactory.createService()
        }
    }
}