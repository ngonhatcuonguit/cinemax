package com.cuongngo.cinemax.response

data class GenresMovieResponse(
    val status_message: String?,
    val status_code: Int?,
    val genres: List<GenresMovie>?
): BaseModel()

data class GenresMovie(
    val id: Int?,
    val name: String?
): BaseModel()
