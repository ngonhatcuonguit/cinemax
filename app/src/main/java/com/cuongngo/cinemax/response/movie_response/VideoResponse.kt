package com.cuongngo.cinemax.response.movie_response

import com.cuongngo.cinemax.response.BaseModel

data class VideoResponse(
    val status_message: String?,
    val status_code: Int?,
    val id: String?,
    var results: List<Video>?
): BaseModel()

data class Video(
    var iso_639_1: String?,
    var iso_3166_1: String?,
    var name: String?,
    var key: String?,
    var site: String?,
    var size: String?,
    var type: String?,
    var official: String?,
    var published_at: String?,
    var id: String?,
): BaseModel()
