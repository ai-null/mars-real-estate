package com.gabutproject.mars_rent.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * TODO:
 * 1. define URI - done
 * 2. define retrofit by calling builder - done
 * 3. implement mars api interface with @GET getProperties returning a string - done
 * 4. Create MarsApi object using retrofit to implement MarsApiService - done
 */

private const val BASE_URL: String = "https://mars.udacity.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    @GET("realestate")
    fun getProperties(): Call<String>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}