package com.ahmetroid.popularmovies.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmetroid.popularmovies.R
import com.bumptech.glide.Glide

@BindingAdapter("image_url")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView)
        .load(url)
        .error(R.drawable.error)
        .into(imageView)
}