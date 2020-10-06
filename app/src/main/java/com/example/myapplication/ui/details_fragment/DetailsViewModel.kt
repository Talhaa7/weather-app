package com.example.myapplication.ui.details_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.NearLocationDetails
import com.example.myapplication.domain.Repository
import com.example.myapplication.utils.Result
import com.example.myapplication.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
class DetailsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _todayWeather = MutableLiveData<NearLocationDetails>()
    val todayWeather: LiveData<NearLocationDetails>
        get() = _todayWeather

    private var _weeklyWeather = MutableLiveData<List<NearLocationDetails>>()
    val weeklyWeather: LiveData<List<NearLocationDetails>>
        get() = _weeklyWeather

    private var _errorString = SingleLiveEvent<String>()
    val errorString: LiveData<String>
        get() = _errorString


    fun getDetails(woeid: Int) {
        repository.getNearLocationDetails(woeid)
            .flowOn(Dispatchers.IO)
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Success -> {
                        _isLoading.value = false
                        result.data?.let { weatherList ->
                            _todayWeather.value = weatherList[0]
                            val weekWeather = mutableListOf(weatherList).removeFirst()
                            _weeklyWeather.value = weekWeather

                        }
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _errorString.value = result.message

                    }
                }
            }
            .launchIn(viewModelScope)

    }
}
