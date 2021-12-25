package com.edurda77.workmaterial.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edurda77.workmaterial.BuildConfig.NASA_API_KEY


import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DailyImageViewModel(
    private val liveDataForViewToObserve: MutableLiveData<DailyImage> = MutableLiveData(),

    private val retrofitImpl: NasaServiceProvider = NasaServiceProvider(),
) :
    ViewModel() {

    fun getImageData(daysAgo: Int): LiveData<DailyImage> {
        sendServerRequest(daysAgo)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(daysAgo: Int) {
        liveDataForViewToObserve.value = DailyImage.Loading(null)

        val apiKey = NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("You need API key"))
        } else {
            executeImageRequest(apiKey, daysAgo)
        }
    }

    private fun executeImageRequest(apiKey: String, daysAgo: Int) {
        val callback = object : Callback<PODServerResponseData> {

            override fun onResponse(
                call: Call<PODServerResponseData>,
                response: Response<PODServerResponseData>
            ) {
                handleImageResponse(response)
            }

            override fun onFailure(call: Call<PODServerResponseData>, throwable: Throwable) {
                liveDataForViewToObserve.value = DailyImage.Error(throwable)
            }
        }

        retrofitImpl.getNasaService().getPictureOfTheDay(apiKey, getDate(daysAgo)).enqueue(callback)
    }

    private fun handleImageResponse(response: Response<PODServerResponseData>) {
        if (response.isSuccessful && response.body() != null) {
            liveDataForViewToObserve.value = DailyImage.Success(response.body()!!)
            return
        }

        val message = response.message()
        if (message.isNullOrEmpty()) {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable("Unidentified error"))
        } else {
            liveDataForViewToObserve.value = DailyImage.Error(Throwable(message))
        }
    }

    fun searchWiki(
        wikiTextView: TextView, inputLayout: TextInputLayout,
        context: Context, savedInstanceState: Bundle?
    ) {
        inputLayout.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://ru.wikipedia.org/wiki/${wikiTextView.text}"
            val uri = Uri.parse(url)
            intent.data = uri
            startActivity(context, intent, savedInstanceState)

        }
    }

    fun getMarsImageToday(): List<Mars> {
        val currentDate = getDate(0)
        val liveDataForMars: MutableList<Mars> = emptyList<Mars>().toMutableList()
        val images: ImagesMars? = retrofitImpl.getNasaService()
            .getMarsImage(currentDate,NASA_API_KEY).execute().body()
        images?.photos?.forEach {
            liveDataForMars.add(it)
        }
        return liveDataForMars
    }
    fun getEarthImageToday() : List<Earth>{

        val liveDataForEarth: MutableList<Earth> = emptyList<Earth>().toMutableList()
        val images = retrofitImpl.getNasaServiceEpic().getEarthImage().execute().body()
        images?.forEach {
            liveDataForEarth.add(it)
        }
        return liveDataForEarth
    }
    @SuppressLint("SimpleDateFormat")
    fun getStringFromDate(): String {
        val startUrl = "https://epic.gsfc.nasa.gov/archive/natural/"
        val addUrl="/jpg/"
        val cal = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        cal.add(Calendar.DATE, -1)
        return startUrl+dateFormat.format(cal.time).toString()+addUrl
    }
    @SuppressLint("SimpleDateFormat")
    fun getDate(daysAgo: Int): String {
        val cal = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DATE, -daysAgo)
        return dateFormat.format(cal.time).toString()
    }


}
