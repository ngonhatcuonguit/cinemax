package com.cuongngo.cinemax.response

import java.io.Serializable

data class MovieResponse(
    val page: Int?,
    val total_pages: Int?,
    val total_results: Int?,
    val results: List<Movie>?
) : BaseModel()

data class Movie(
    val id: String?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Float?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Float?,
    val vote_count: Long?
): BaseModel()

data class Dates(
    val maximum: String?,
    val minimum: String?
): BaseModel()

