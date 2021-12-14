package com.edurda77.workmaterial.model

import com.edurda77.workmaterial.BuildConfig.NASA_API_KEY
import com.edurda77.workmaterial.domain.NasaApi
import com.edurda77.workmaterial.domain.NasaRepoUseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.nasa.gov/"

class NasaRepoUseCaseImpl : NasaRepoUseCase {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private var api: NasaApi = retrofit.create(NasaApi::class.java)
    override fun getFotoOfTheDaySync(): PODServerResponseData? {

        return api.getPictureOfTheDay(NASA_API_KEY)
            .execute().body()
    }

    override fun getFotoOfTheDayAsync(
        onSuccess: (PODServerResponseData?) -> Unit,
        OnError: (Throwable) -> Unit
    ) {
        api.getPictureOfTheDay(NASA_API_KEY).enqueue(object :
            Callback<PODServerResponseData> {
            override fun onResponse(
                call: Call<PODServerResponseData>,
                response: Response<PODServerResponseData>
            ) {
                if (response.isSuccessful) {
                    onSuccess(response.body()!!)
                } else {
                    OnError(Throwable("Неизвестная ошибка"))
                }

            }

            override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                OnError(t)
            }


        })
    }

}