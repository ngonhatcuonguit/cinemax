package com.cuongngo.cinemax.response.tv_response

import com.cuongngo.cinemax.response.BaseModel
import com.cuongngo.cinemax.response.movie_response.GenresMovie
import com.cuongngo.cinemax.response.ProductionCompanies

data class TvDetailResponse(
    val id: Int?,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genres: List<GenresMovie>?,
    val first_air_date: String?,
    val homepage: String?,
    val in_production: Boolean?,
    val languages: List<String>?,
    val last_episode_to_air: EpisodeToAir,
    val next_episode_to_air: EpisodeToAir,
    val networks: List<Network>?,
    val last_air_date: String?,
    val name: String?,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val origin_country: List<String>?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
//    val popularity: Long?,
    val poster_path: String?,
    val status: String?,
    val tagline: String?,
    val type: String?,
    val vote_average: Float?,
    val vote_count: Long?,
    val production_companies: List<ProductionCompanies>?
): BaseModel()

data class EpisodeToAir(
    val air_date: String?,
    val episode_number: Int?,
    val id: Int?,
    val name: String?,
    val overview: String?,
    val production_code: String?,
    val vote_average: Float?,
    val vote_count: Long?,
    val still_path: String?,
    val season_number: Int?,
    val runtime: Int?,
): BaseModel()

data class Network(
    val name: String?,
    val id: Int?,
    val logo_path: String?,
    val origin_country: String?,
):BaseModel()