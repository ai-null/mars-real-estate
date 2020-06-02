package com.gabutproject.mars_rent.overview

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

    private val _selectedItemData = MutableLiveData<MarsProperty>()
    val selectedItemData: LiveData<MarsProperty> get() = _selectedItemData

    init {
        // pass show all as a default value
        // we want to show all items in the first time
        getMarsRealEstateData(MarsApiFilter.SHOW_ALL)
    }

    private fun getMarsRealEstateData(type: MarsApiFilter) {
        uiScope.launch {
            try {
                // set status to loading and update to done after fetching is completed
                _status.value = MarsApiStatus.LOADING
                val listResult = MarsApi.retrofitService.getPropertiesAsync(type.value)
                _status.value = MarsApiStatus.DONE

                // set the value
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateListFilter(type: MarsApiFilter) {
        getMarsRealEstateData(type)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onSelectedItemNavigate(marsProperty: MarsProperty) {
        _selectedItemData.value = marsProperty
    }

    fun onSelectedItemNavigateComplete() {
        _selectedItemData.value = null
    }
}