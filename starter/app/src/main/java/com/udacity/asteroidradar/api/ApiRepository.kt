package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.AsteroidsNeoWs
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call

object ApiRepository : ApiService {

    private val apiService = RetrofitClient.getApiService()

    override fun getPictureOfDay(): Call<PictureOfDay> {
        return apiService.getPictureOfDay()
    }

    override fun getAsteroidsNeoWs(startDate: String, endDate: String): Call<AsteroidsNeoWs> {
        return apiService.getAsteroidsNeoWs(startDate, endDate)
    }

}