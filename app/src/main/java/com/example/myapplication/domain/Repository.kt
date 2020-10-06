package com.example.myapplication.domain

import com.example.myapplication.utils.Result
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getNearLocations(lattLng: String) : Flow<Result<List<NearLocation>>>
    fun getNearLocationDetails(
        woeid: Int
    ) : Flow<Result<List<NearLocationDetails>>>
}