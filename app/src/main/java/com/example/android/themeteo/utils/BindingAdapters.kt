package com.example.android.themeteo.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.themeteo.R
import com.example.android.themeteo.domains.Weather
import com.example.android.themeteo.weather.WeatherAdapter

//@BindingAdapter("listData")
//fun bindRecyclerView(recyclerView: RecyclerView, data: Weather?) {
//    val adapter = recyclerView.adapter as WeatherAdapter
//    adapter.submitList(data)
//}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}