package com.cuongngo.cinemax.response

data class PersonalResponse(
    var status_message: String?,
    var status_code: Int?,
    var page: Int?,
    var total_pages: Int?,
    var total_results: Int?,
    var results: List<Personal>?
): BaseModel()

data class Personal(
    val id: String?,
    val adult: Boolean?,
    val profile_path: String?,
    val genre_ids: List<Int>?,
    var name: String?
):BaseModel()
