package com.cuongngo.cinemax.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cuongngo.cinemax.R
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