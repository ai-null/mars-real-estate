package com.gabutproject.mars_rent.detail

import android.app.Application
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.gabutproject.mars_rent.R
import com.gabutproject.mars_rent.network.MarsProperty

class DetailViewModel(selectedMarsProperty: MarsProperty, app: Application) : ViewModel() {
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty> get() = _selectedProperty

    init {
        _selectedProperty.value = selectedMarsProperty
    }

    val displayPropertyPrice: LiveData<String> = selectedProperty.map {
        app.applicationContext.getString(
            when (it.rental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price
        )
    }

    val displayPropertyType: LiveData<String> = selectedProperty.map {
        app.applicationContext.getString(
            when (it.rental) {
                true -> R.string.type_rent
                else -> R.string.type_buy
            }
        )
    }

    val displayPropertyDescription: LiveData<Spanned> = selectedProperty.map {
        Html.fromHtml(it.description)
    }
}