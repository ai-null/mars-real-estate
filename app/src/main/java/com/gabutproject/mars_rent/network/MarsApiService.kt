package com.gabutproject.mars_rent.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * TODO:
 * 1. define URI - done
 * 2. define retrofit by calling builder - done
 * 3. implement mars api interface with @GET getProperties returning a string - done
 * 4. Create MarsApi object using retrofit to implement MarsApiService - done
 */

private const val BASE_URL: String = "https://mars.udacity.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MarsApiService {
    @GET("realestate")
    suspend fun getPropertiesAsync(): List<MarsProperty>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}