package com.cuongngo.cinemax.response

import com.cuongngo.cinemax.response.tv_response.TvDetailResponse

data class MultiMediaResponse(
    val status_message: String?,
    val status_code: Int?,
    val page: Int?,
    val total_pages: Int?,
    val total_results: Int?,
    val results: List<MultiMedia>?
): BaseModel()

data class MultiMedia(
    val id: String?,
    val media_type: String?,
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
    val vote_count: Long?,
    val name: String?,
    val origin_country: List<String>?,
    val original_name: String?,
    val first_air_date: String?
): BaseModel()

data class MediaDetailResponse(
    var movieDetail: MovieDetailResponse?,
    var tvDetail: TvDetailResponse?,
    var media_type: String?
): BaseModel(){
    fun setMovieData(movie: MovieDetailResponse){
        movieDetail = movie
    }
    fun setTvData(tv: TvDetailResponse){
        tvDetail = tv
    }
    fun setMediaType(type: String){
        media_type = type
    }
}
