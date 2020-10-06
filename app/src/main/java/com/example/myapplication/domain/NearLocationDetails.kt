package com.example.myapplication.domain

import com.squareup.moshi.Json

data class NearLocationDetails (
    @Json(name ="id") val id : Long,
    @Json(name ="weather_state_name") val weatherStateName : String?,
    @Json(name ="weather_state_abbr") val weatherStateAbbr : String?,
    @Json(name ="wind_direction_compass") val windDirectionCompass : String?,
    @Json(name ="created") val created : String?,
    @Json(name ="applicable_date") val applicableDate : String?,
    @Json(name ="min_temp") val minTemp : Double?,
    @Json(name ="max_temp") val maxTemp : Double?,
    @Json(name ="the_temp") val theTemp : String?,
    @Json(name ="wind_speed") val windSpeed : Double?,
    @Json(name ="wind_direction") val windDirection : Double?,
    @Json(name ="air_pressure") val airPressure : String?,
    @Json(name ="humidity") val humidity : Int?,
    @Json(name ="visibility") val visibility : Double?,
    @Json(name ="predictability") val predictability : Int?){


}