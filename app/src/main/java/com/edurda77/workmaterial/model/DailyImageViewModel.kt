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
import com.edurda77.workmaterial.BuildConfig
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DailyImageViewModel (
    private val liveDataForViewToObserve: MutableLiveData<DailyImage> = MutableLiveData(),
    private val retrofitImpl: NasaRepoUseCaseImpl = NasaRepoUseCaseImpl(),
) :
    ViewModel() {

    fun getImageData(): LiveData<DailyImage> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = DailyImage.Loading(null)

        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            DailyImage.Error(Throwable("You need API key"))
        } else {
            executeImageRequest(apiKey)
        }
    }

    private fun executeImageRequest(apiKey: String) {
        val callback = object : Callback<PODServerResponseData> {

            override fun onResponse(
                call: Call<PODServerResponseData>,
                response: Response<PODServerResponseData>
            ) {
                handleImageResponse(response)
            }

            override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                liveDataForViewToObserve.value = DailyImage.Error(t)
            }
        }

        retrofitImpl.getNasaService().getPictureOfTheDay(apiKey).enqueue(callback)
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
    fun searchWiki(wikiTextView: TextView, inputLayout: TextInputLayout,
                           context: Context, savedInstanceState: Bundle?) {
        inputLayout.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://ru.wikipedia.org/wiki/${wikiTextView.text}"
            val uri = Uri.parse(url)
            intent.data = uri
            startActivity(context, intent,savedInstanceState)

        }
    }
    @SuppressLint("SimpleDateFormat")
    fun getDate() : String {
        val cal: Calendar = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(cal.add(Calendar.DATE, -0))
    }
}
