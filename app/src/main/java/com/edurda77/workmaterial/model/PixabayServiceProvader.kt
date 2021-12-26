package com.edurda77.workmaterial.model

import com.edurda77.workmaterial.domain.PixabayApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL_PIXABAY = "https://pixabay.com/"
const val LANGUAGE = "en"

class PixabayServiceProvader {
    fun getPixabayService(): PixabayApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_PIXABAY)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        return retrofit.create(PixabayApi::class.java)
    }


    private fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }
}