package com.udacity.asteroidradar.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsNeoWs
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.TypeFilter
import com.udacity.asteroidradar.api.ApiRepository
import com.udacity.asteroidradar.room.AppDatabase
import com.udacity.asteroidradar.room.AsteroidDao
import com.udacity.asteroidradar.room.AsteroidModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val apiRepository = ApiRepository
    private lateinit var asteroidDao: AsteroidDao

    private val _showErrorLiveData = MutableLiveData<String>()
    val showErrorLiveData: LiveData<String>
        get() = _showErrorLiveData

    private val _getPictureOfDayLiveData = MutableLiveData<PictureOfDay?>()
    val getPictureOfDayLiveData: LiveData<PictureOfDay?>
        get() = _getPictureOfDayLiveData

    val allAsteroids: LiveData<List<AsteroidModel>>
        get() = asteroidDao.getAllAsteroids()

    val savedAsteroids: LiveData<List<AsteroidModel>?>
        get() = asteroidDao.getSavedAsteroids()

    var listAllAsteroids = listOf<AsteroidModel>()
    var listSavedAsteroids = listOf<AsteroidModel>()
    var listAsteroidsByWeek = listOf<AsteroidModel>()
    var listAsteroidsToDay = listOf<AsteroidModel>()
    var typeFilter = TypeFilter.WEEK.type
    fun initDataBase(context: Context) {
        val dataBase = AppDatabase.getDatabase(context)
        asteroidDao = dataBase.asteroidDao()
    }

    fun getPictureOfDay() {
        val call: Call<PictureOfDay> = apiRepository.getPictureOfDay()
        call.enqueue(object : Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                if (response.isSuccessful) {
                    _getPictureOfDayLiveData.postValue(response.body())
                } else {
                    _showErrorLiveData.postValue(response.message())
                }
            }
            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                _showErrorLiveData.postValue(t.message)
            }
        })
    }

    fun saveAsteroid(id: Long) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            asteroidDao.savedAsteroids(id)
        }
    }

    fun unSaveAsteroid(id: Long) {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            asteroidDao.unSavedAsteroids(id)
        }
    }
}