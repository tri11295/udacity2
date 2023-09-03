package com.udacity.asteroidradar

import android.app.Application;
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager;

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val initialAsteroidWorkRequest = OneTimeWorkRequest.Builder(AsteroidWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(initialAsteroidWorkRequest)
    }
}