package com.gabutproject.mars_rent.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gabutproject.mars_rent.network.MarsProperty

class DetailViewModelFactory(private val selectedMarsProperty: MarsProperty, private val app: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(selectedMarsProperty, app) as T
        }
        throw IllegalArgumentException("Unknown class")
    }

}