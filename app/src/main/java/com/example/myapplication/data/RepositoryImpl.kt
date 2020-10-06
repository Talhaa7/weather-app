package com.example.myapplication.data

import com.example.myapplication.domain.NearLocation
import com.example.myapplication.domain.NearLocationDetails
import com.example.myapplication.domain.Repository
import com.example.myapplication.utils.NoConnectionInterceptor
import com.example.myapplication.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RepositoryImpl  @Inject constructor(
    private val webAPI: WebAPI
) : Repository{
    override fun getNearLocations(lattLng: String) = flow<Result<List<NearLocation>>> {
        try {

            emit(Result.Loading())

            val response = webAPI.getNearLocations(lattLng)

            if (response.isSuccessful)
                emit(Result.Success(data = response.body()!!))
            else
                emit(Result.Error(message = "Unexpected Error"))

        } catch (e: NoConnectionInterceptor.NoConnectivityException) {

            emit(Result.Error(e.message))
        }catch (e: NoConnectionInterceptor.NoInternetException) {

            emit(Result.Error(e.message))
        }catch (e: Exception) {

            emit(Result.Error("Unexpected Error"))
        }
    }
    override fun getNearLocationDetails(
        woeid: Int
    ) = flow<Result<List<NearLocationDetails>>> {
        try {

            emit(Result.Loading())

            val response = webAPI.getNearLocationDetails(woeid)

            if (response.isSuccessful)
                emit(Result.Success(data = response.body()?.consolidatedWeather ?: listOf()))
            else
                emit(Result.Error(message = "Unexpected Error"))

        } catch (e: NoConnectionInterceptor.NoConnectivityException) {

            emit(Result.Error(e.message))
        }catch (e: NoConnectionInterceptor.NoInternetException) {

            emit(Result.Error(e.message))
        }catch (e: Exception) {
            println(e)

            emit(Result.Error("Unexpected Error"))
        }
    }
}