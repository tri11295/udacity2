package com.udacity.asteroidradar

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.udacity.asteroidradar.room.AsteroidModel
import java.io.Serializable

data class Asteroid(
    val id: Long,
    val name: String,
    @SerializedName("close_approach_data")
    val closeApproachDate: List<CloseApproachData> = listOf(),
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitude: Double,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter?,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean
) : Serializable {

    fun convertToAsteroidModel(): AsteroidModel {
        val hasCloseData = closeApproachDate.isNotEmpty()
        return AsteroidModel(
            id = id,
            name = name,
            closeApproachDate = if (hasCloseData) closeApproachDate.first().closeApproachDate.orEmpty() else "",
            absoluteMagnitude = absoluteMagnitude,
            kilometersPerSecond = if (hasCloseData) closeApproachDate.first().relativeVelocity?.kilometersPerSecond
                ?: 0.0 else 0.0,
            estimatedDiameter = estimatedDiameter?.kilometers?.estimatedDiameterMax ?: 0.0,
            astronomical = if (hasCloseData) closeApproachDate.first().missDistance?.astronomical
                ?: 0.0 else 0.0,
            isPotentiallyHazardous = isPotentiallyHazardous
        )
    }
}

data class CloseApproachData(
    @SerializedName("close_approach_date")
    val closeApproachDate: String? = null,
    @SerializedName("close_approach_date_full")
    val closeApproachDateFull: String? = null,
    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity? = null,
    @SerializedName("miss_distance")
    val missDistance: MissDistance? = null
) : Serializable

data class EstimatedDiameter(
    @SerializedName("kilometers")
    val kilometers: Kilometers? = null
) : Serializable

data class Kilometers(
    @SerializedName("estimated_diameter_min")
    val estimatedDiameterMin: Double? = null,
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double? = null
) : Serializable

data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: Double? = null,
    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: Double? = null,
    @SerializedName("miles_per_hour")
    val milesPerHour: Double? = null
) : Serializable

data class MissDistance(
    val astronomical: Double? = null,
    val lunar: Double? = null,
    val kilometers: Double? = null,
    val miles: Double? = null
) : Serializable