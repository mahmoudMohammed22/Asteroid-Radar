package com.udacity.asteroidradar.networkpicture

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()


interface ApiPictureOfDay{
    @GET("neo/rest/v1/feed")
    suspend fun getInfo(
        @Query("start_date") START_DATE:String,
        @Query("end_date") END_DATE:String,
        @Query("api_key") apiKey:String
    ): String

    @GET("planetary/apod")
    suspend fun getPicture(
        @Query("api_key") apiKey:String
    ):PictureOfDay
}


object ApiPictureNetwork{
    val PictureServier :ApiPictureOfDay by lazy {
        retrofit.create(ApiPictureOfDay::class.java)
    }

}