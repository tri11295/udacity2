package com.udacity.asteroidradar

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AsteroidsNeoWs(
    @SerializedName("element_count")
    val elementCount: Int,
    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<Asteroid>>
) : Serializable

data class NearEarthObjects(
    val data: Map<String, List<Asteroid>>
) : Serializable