package com.cuongngo.cinemax.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cuongngo.cinemax.R
import com.cuongngo.cinemax.response.MultiMedia
import com.cuongngo.cinemax.utils.Constants.Companion.POSTER_BASE_URL

@BindingAdapter("loadImagePath")
fun loadImagePath(view: ImageView, posterPath: String?) {
    val url = POSTER_BASE_URL + posterPath
    Glide.with(view)
        .load(url)
        .placeholder(R.color.dark)
        .apply(RequestOptions.centerCropTransform())
        .into((view))
}

@BindingAdapter("bindName")
fun bindName(textView: TextView, multiMedia: MultiMedia){
    if (multiMedia.media_type == "tv"){
        textView.text = multiMedia.name
    }else textView.text = multiMedia.title
}

@BindingAdapter("bindDate")
fun bindDate(textView: TextView, multiMedia: MultiMedia){
    if (multiMedia.media_type == "tv"){
        textView.text = multiMedia.first_air_date
    }else textView.text = multiMedia.release_date
}