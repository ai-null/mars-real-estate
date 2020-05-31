package com.gabutproject.mars_rent.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gabutproject.mars_rent.network.MarsApi
import com.gabutproject.mars_rent.network.MarsApiFilter
import com.gabutproject.mars_rent.network.MarsProperty
import kotlinx.coroutines.*
import java.lang.Exception

enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus> get() = _status

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>> get() = _properties

    init {
        getMarsRealEstateData(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateData(filter: MarsApiFilter) {
        uiScope.launch {
            try {
                // set loading, and update the value to done after fetching is done
                _status.value = MarsApiStatus.LOADING
                val listResult = MarsApi.retrofitService.getPropertiesAsync(filter.value)
                _status.value = MarsApiStatus.DONE

                // set the value
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateData(filter)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}