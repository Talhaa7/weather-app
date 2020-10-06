package com.example.myapplication.data

import com.example.myapplication.domain.Details
import com.example.myapplication.domain.NearLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebAPI {
    @GET("location/search")
    suspend fun getNearLocations(@Query("lattlong") lattLng : String) : Response<List<NearLocation>>

    @GET("location/{woeid}")
    suspend fun getNearLocationDetails(
        @Path("woeid")woeid : Int
    ) : Response<Details>

}