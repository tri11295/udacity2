package com.udacity.asteroidradar

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.ApiRepository
import com.udacity.asteroidradar.room.AppDatabase
import com.udacity.asteroidradar.room.AsteroidModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class AsteroidWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val apiRepository = ApiRepository
        val dataBase = AppDatabase.getDatabase(context)
        val asteroidDao = dataBase.asteroidDao()
        val listDayOfWeek = getNextSevenDaysFormattedDates()

        val call: Call<AsteroidsNeoWs> =
            apiRepository.getAsteroidsNeoWs(listDayOfWeek.first(), listDayOfWeek.last())
        call.enqueue(object : Callback<AsteroidsNeoWs> {
            override fun onResponse(
                call: Call<AsteroidsNeoWs>,
                response: Response<AsteroidsNeoWs>
            ) {
                if (response.isSuccessful) {
                    val coroutineScope = CoroutineScope(Dispatchers.IO)
                    coroutineScope.launch {
                        val result = response.body()
                        val listAsteroidModel = mutableListOf<AsteroidModel>()
                        result?.nearEarthObjects?.forEach {
                            listAsteroidModel.addAll(it.value.map { asteroid ->
                                asteroid.convertToAsteroidModel()
                            })
                        }
                        asteroidDao.insertAsteroidsByWeek(listAsteroidModel)
                    }
                }
            }

            override fun onFailure(call: Call<AsteroidsNeoWs>, t: Throwable) {
            }
        })

        scheduleRecurringWork()

        return Result.success()
    }

    private fun scheduleRecurringWork() {
        val asteroidWorkRequest = PeriodicWorkRequest.Builder(
            AsteroidWorker::class.java, 15, TimeUnit.DAYS
        ).build()
        WorkManager.getInstance(applicationContext).enqueue(asteroidWorkRequest)
    }

    private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()
        for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return formattedDateList
    }

}