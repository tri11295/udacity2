package com.udacity.asteroidradar

import android.app.Application;
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager;

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true).build()
        val initialAsteroidWorkRequest = OneTimeWorkRequest
            .Builder(AsteroidWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(initialAsteroidWorkRequest)
    }
}