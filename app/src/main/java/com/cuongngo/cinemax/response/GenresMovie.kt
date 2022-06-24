package com.cuongngo.cinemax.response

data class GenresMovieResponse(
    val status_message: String?,
    val status_code: Int?,
    val genres: ArrayList<GenresMovie>?
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
