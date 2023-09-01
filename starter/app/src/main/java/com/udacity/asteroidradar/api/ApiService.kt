package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.AsteroidsNeoWs
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("planetary/apod")
    fun getPictureOfDay(): Call<PictureOfDay>

    @GET("neo/rest/v1/feed")
    fun getAsteroidsNeoWs(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Call<AsteroidsNeoWs>
}