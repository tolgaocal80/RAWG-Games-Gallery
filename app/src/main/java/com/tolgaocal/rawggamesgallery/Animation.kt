package com.tolgaocal.rawggamesgallery

import android.view.View

private const val DURATION = 200L

fun View.showAnimation() {
    if (this.visibility == View.VISIBLE) {
        return
    }
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate().alpha(1f).setDuration(DURATION).start()
}

fun View.hideAnimation() {
    if (this.visibility == View.GONE) {
        return
    }
    this.animate().alpha(0f).setDuration(DURATION).start()
    this.postDelayed({this.visibility = View.GONE}, DURATION)
}