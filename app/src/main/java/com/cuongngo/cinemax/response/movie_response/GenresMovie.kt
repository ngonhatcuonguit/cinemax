package com.cuongngo.cinemax.response.movie_response

import com.cuongngo.cinemax.response.BaseModel

data class GenresMovieResponse(
    var status_message: String?,
    var status_code: Int?,
    var genres: ArrayList<GenresMovie>?
): BaseModel(){
    fun addGenres(list: ArrayList<GenresMovie>){
        genres?.addAll(list)
    }
}

data class GenresMovie(
    val id: Int?,
    val name: String?,
    var is_selected: Boolean = false
): BaseModel()
