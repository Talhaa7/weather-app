package com.example.myapplication.domain

import com.squareup.moshi.Json
import java.io.Serializable

data class NearLocation(
    val title: String?,
    @Json(name = "location_type")
    val locationType: String?,
    @Json(name = "latt_long")
    val lattLong: String?,
    val woeid: Int,
    val distance: Int?
) : Serializable