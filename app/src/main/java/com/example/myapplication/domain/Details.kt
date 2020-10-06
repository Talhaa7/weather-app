package com.example.myapplication.domain

import com.squareup.moshi.Json

data class Details (
    @Json(name ="consolidated_weather") val consolidatedWeather : List<NearLocationDetails>
)