package com.udacity.asteroidradar

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import java.io.Serializable

data class PictureOfDay(
    @SerializedName("media_type")
    val mediaType: String,
    val title: String,
    val url: String
) : Serializable