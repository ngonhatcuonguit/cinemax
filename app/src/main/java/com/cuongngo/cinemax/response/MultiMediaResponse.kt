package com.cuongngo.cinemax.response

import com.cuongngo.cinemax.response.tv_response.TvDetailResponse

data class MultiMediaResponse(
    val status_message: String?,
    val status_code: Int?,
    val page: Int?,
    val total_pages: Int?,
    val total_results: Int?,
    val results: List<MultiMedia>?
) : BaseModel()

data class MultiMedia(
    var id: String?,
    var media_type: String?,
    var adult: Boolean?,
    var backdrop_path: String?,
    var genre_ids: List<Int>?,
    var original_language: String?,
    var original_title: String?,
    var overview: String?,
    var popularity: Float?,
    var poster_path: String?,
    var release_date: String?,
    var title: String?,
    var video: Boolean?,
    var vote_average: Float?,
    var vote_count: Long?,
    var name: String?,
    var origin_country: List<String>?,
    var original_name: String?,
    val first_air_date: String?,
    var known_for: List<MultiMedia>?,
    var known_for_department: String?
) : BaseModel()

data class MediaDetailResponse(
    var movieDetail: MovieDetailResponse?,
    var tvDetail: TvDetailResponse?,
    var media_type: String?
) : BaseModel() {
    fun setMovieData(movie: MovieDetailResponse) {
        movieDetail = movie
    }

    fun setTvData(tv: TvDetailResponse) {
        tvDetail = tv
    }

    fun setMediaType(type: String) {
        media_type = type
    }
}
