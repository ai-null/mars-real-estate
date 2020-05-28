package com.gabutproject.mars_rent.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabutproject.mars_rent.network.MarsApi
import com.gabutproject.mars_rent.network.MarsProperty
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val response: LiveData<String> get() = _response

    init {
        getMarsRealEstateData()
    }

    private fun getMarsRealEstateData() {
        uiScope.launch {
            something()
            _response.value = "Set Mars API response here!"
        }
    }

    private suspend fun something() {
        // TODO: Call the MarsApi to enqueue the retrofit request, implementing callbacks

        withContext(Dispatchers.IO) {
            MarsApi.retrofitService.getProperties().enqueue(object : Callback<List<MarsProperty>> {
                override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                    _response.value = "failure" + t.message
                }

                override fun onResponse(
                    call: Call<List<MarsProperty>>,
                    response: Response<List<MarsProperty>>
                ) {
                    _response.value =
                        "Success: ${response.body()?.size.toString()} Mars property retrieved"
                }
            })
        }
    }
}