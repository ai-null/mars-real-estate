package com.gabutproject.mars_rent.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * TODO:
 * 1. define URI - done
 * 2. define retrofit by calling builder - done
 * 3. implement mars api interface with @GET getProperties returning a string - done
 * 4. Create MarsApi object using retrofit to implement MarsApiService - done
 * 5. Put mark on each list to show which to buy or rent - done
 * 6. Make overflow buttons works to filter the list by its isRental
 *      - put unique id to overflow menu - DONE
 *      - make enum class that presents SHOW_ALL, SHOW_BUY, & SHOW_RENT - DONE
 *      - make it clickable
 */

enum class MarsApiFilter(val value: String) {
    SHOW_ALL("all"),
    SHOW_RENT("rent"),
    SHOW_BUY("buy")
}

private const val BASE_URL: String = "https://mars.udacity.com/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface MarsApiService {
    @GET("realestate")
    suspend fun getPropertiesAsync(@Query("filter") type: String): List<MarsProperty>
}

object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}