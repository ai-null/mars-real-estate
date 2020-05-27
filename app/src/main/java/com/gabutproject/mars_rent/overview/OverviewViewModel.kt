package com.gabutproject.mars_rent.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OverviewViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()

    val response: LiveData<String> get() = _response

    init {
        getMarsRealEstateData()
    }

    private fun getMarsRealEstateData() {
        _response.value = "Set Mars API response here!"
    }
}