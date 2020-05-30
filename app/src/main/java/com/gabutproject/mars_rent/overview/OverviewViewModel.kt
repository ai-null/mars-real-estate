package com.gabutproject.mars_rent.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabutproject.mars_rent.network.MarsApi
import com.gabutproject.mars_rent.network.MarsProperty
import kotlinx.coroutines.*
import java.lang.Exception

class OverviewViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String> get() = _response

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _property = MutableLiveData<MarsProperty>()
    val property: LiveData<MarsProperty> get() = _property

    init {
        getMarsRealEstateData()
    }

    private fun getMarsRealEstateData() {
        uiScope.launch {
            _response.value = "Set Mars API response here!"

            val getPropertiesDeferred = MarsApi.retrofitService.getPropertiesAsync()

            try {
                _response.value =
                    "data retrieved with: ${getPropertiesDeferred.size} of data & ${getPropertiesDeferred[0].id}"

                if (getPropertiesDeferred.isNotEmpty()) {
                    _property.value = getPropertiesDeferred[0]
                }
            } catch (e: Exception) {
                _response.value = "error ${e.message}"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}