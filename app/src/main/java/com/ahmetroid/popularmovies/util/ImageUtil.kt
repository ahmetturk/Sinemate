package com.ahmetroid.popularmovies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmetroid.popularmovies.R
import com.bumptech.glide.Glide

private const val POSTER_URL = "https://image.tmdb.org/t/p/w342"

@BindingAdapter("image_url")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView)
        .load(POSTER_URL + url)
        .error(R.drawable.error)
        .into(imageView)
}