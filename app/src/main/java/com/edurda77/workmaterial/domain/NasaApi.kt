package com.edurda77.workmaterial.domain

import com.edurda77.workmaterial.model.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        //@Query("date") date: String - будет реализован
    ): Call<PODServerResponseData>


}