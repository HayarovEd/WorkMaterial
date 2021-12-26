package com.edurda77.workmaterial.domain

import com.edurda77.workmaterial.model.SearchedPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {
    @GET("api/")
    fun getPhoto(
        @Query("key") apiKey: String,
        @Query("q") quest: String,
        @Query("lang") lang: String,
    ): Call<SearchedPhoto>
}