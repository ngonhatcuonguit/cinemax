package com.cuongngo.cinemax.response

import com.cuongngo.cinemax.response.movie_response.GenresMovie

data class MovieDetailResponse(
    val status_message: String?,
    val status_code: Int?,
    val id: String?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val budget: Long?,
    val genre_ids: List<Int>?,
    val genres: List<GenresMovie>?,
    val homepage: String?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Float?,
    val poster_path: String?,
    val production_companies: List<ProductionCompanies>?,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Float?,
    val vote_count: Long?
): BaseModel()

data class ProductionCompanies(
    val id: String?,
    val name: String?,
    val logo_path: String?,
    val origin_country: String?,
): BaseModel()