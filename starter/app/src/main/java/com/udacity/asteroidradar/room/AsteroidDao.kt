package com.udacity.asteroidradar.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAsteroidsByWeek(asteroids: List<AsteroidModel>)

    @Query("SELECT * FROM asteroids")
    fun getAllAsteroids(): LiveData<List<AsteroidModel>>

    @Query("SELECT * FROM asteroids where isSaved = 1")
    fun getSavedAsteroids(): LiveData<List<AsteroidModel>?>

    @Query("update asteroids set isSaved = 1 where id = :id")
    fun savedAsteroids(id: Long)

    @Query("update asteroids set isSaved = 0 where id = :id")
    fun unSavedAsteroids(id: Long)
}