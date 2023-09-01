package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.isVisible() = visibility == View.VISIBLE
fun View.isGone() = visibility == View.GONE
fun View.isInvisible() = visibility == View.INVISIBLE

fun View.visible() {
    if (!isVisible()) visibility = View.VISIBLE
}

fun View.gone() {
    if (!isGone()) visibility = View.GONE
}

fun View.invisible() {
    if (!isInvisible()) visibility = View.INVISIBLE
}

fun ImageView.loadUrl(url : String) {
    Glide.with(this).load(url).into(this)
}
