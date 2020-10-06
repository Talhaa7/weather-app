package com.example.myapplication.ui.near_location_fragment

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.NearLocation
import com.example.myapplication.domain.Repository
import com.example.myapplication.utils.Result
import com.example.myapplication.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class NearLocationsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _nearLocationList = MutableLiveData<List<NearLocation>>()
    val nearLocationList: LiveData<List<NearLocation>>
        get() = _nearLocationList

    private var _errorString = SingleLiveEvent<String>()
    val errorString: LiveData<String>
        get() = _errorString

    fun nearLocations(locX: Double, locY: Double) {
        if (_nearLocationList.value.isNullOrEmpty()) {
            repository
                .getNearLocations("$locX,$locY")
                .flowOn(Dispatchers.IO)
                .onEach { result ->
                    when (result) {
                        is Result.Loading -> {
                            _isLoading.value = true
                        }
                        is Result.Success -> {
                            _isLoading.value = false
                            _nearLocationList.value = result.data ?: listOf()
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

}