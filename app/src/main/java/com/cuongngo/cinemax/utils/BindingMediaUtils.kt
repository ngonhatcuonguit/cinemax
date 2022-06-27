package com.cuongngo.cinemax.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cuongngo.cinemax.response.MediaDetailResponse
import com.cuongngo.cinemax.response.MultiMedia
import com.cuongngo.cinemax.utils.Constants.Companion.POSTER_BASE_URL

@BindingAdapter("loadImagePath")
fun loadImagePath(view: ImageView, posterPath: String?) {
    val url = POSTER_BASE_URL + posterPath
    Glide.with(view)
        .load(url)
        .apply(RequestOptions().placeholder(MyDrawableCompat.createProgressDrawable(view.context)))
        .apply(RequestOptions.centerCropTransform())
        .into((view))
}

@BindingAdapter("bindName")
fun bindName(textView: TextView, multiMedia: MultiMedia?) {
    if (multiMedia?.media_type == "tv") {
        textView.text = multiMedia.name
    } else textView.text = multiMedia?.title
}

@BindingAdapter("bindDate")
fun bindDate(textView: TextView, multiMedia: MultiMedia?) {
    if (multiMedia?.media_type == "tv") {
        textView.text = multiMedia.first_air_date
    } else textView.text = multiMedia?.release_date
}

@BindingAdapter("bindMediaDetailDate")
fun bindMediaDetailDate(textView: TextView, mediaDetailResponse: MediaDetailResponse) {
    if (mediaDetailResponse.media_type == "tv") {
        textView.text = mediaDetailResponse.tvDetail?.first_air_date
    } else textView.text = mediaDetailResponse.movieDetail?.release_date
}

@BindingAdapter("roundRating")
fun roundRating(textView: TextView, vote: Float){
    val rounded = String.format("%.1f", vote)
    textView.text = rounded
}

@BindingAdapter("bindOverView")
fun bindOverView(textView: TextView, mediaDetailResponse: MediaDetailResponse?){
    if (mediaDetailResponse?.media_type == Constants.MediaType.TV) {
        textView.text = mediaDetailResponse.tvDetail?.overview
    } else textView.text = mediaDetailResponse?.movieDetail?.overview
}