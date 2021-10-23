package com.tolgaocal.rawggamesgallery

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

object Constants {

    const val API_KEY = "a1e73b6fa00b49abafdf5677021cb060"

    fun fetchImage(url: String, imageView: ImageView, activity: AppCompatActivity) {
        Glide.with(activity).load(url).centerCrop().into(imageView)
    }

}