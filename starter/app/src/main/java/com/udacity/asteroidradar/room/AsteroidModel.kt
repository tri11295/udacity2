package com.udacity.asteroidradar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "asteroids")
data class AsteroidModel(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "closeApproachDate")
    val closeApproachDate: String,
    @ColumnInfo(name = "absoluteMagnitude")
    val absoluteMagnitude: Double,
    @ColumnInfo(name = "kilometersPerSecond")
    val kilometersPerSecond: Double,
    @ColumnInfo(name = "estimatedDiameter")
    val estimatedDiameter: Double,
    @ColumnInfo(name = "astronomical")
    val astronomical: Double,
    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean,
    @ColumnInfo(name = "isSaved")
    var isSaved : Int = 0
) : Serializable