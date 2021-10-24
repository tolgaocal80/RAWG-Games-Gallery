package com.tolgaocal.rawggamesgallery

import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

object Commons {

    private val DURATION = 200L

    fun fetchImage(url: String, imageView: ImageView, activity: AppCompatActivity) {
        Glide.with(activity).load(url).centerCrop().into(imageView)
    }

    fun View.showView() {
        if (this.visibility == View.VISIBLE) {
            return
        }
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate().alpha(1f).setDuration(DURATION).start()
    }

    fun View.hideView() {
        if (this.visibility == View.GONE) {
            return
        }
        this.animate().alpha(0f).setDuration(DURATION).start()
        this.postDelayed({this.visibility = View.GONE}, DURATION)
    }


}