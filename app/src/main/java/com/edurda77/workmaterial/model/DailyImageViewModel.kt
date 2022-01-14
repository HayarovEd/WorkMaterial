package com.edurda77.workmaterial.model


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.api.load
import com.edurda77.workmaterial.BuildConfig.NASA_API_KEY
import com.edurda77.workmaterial.BuildConfig.PIXABAY_API_KEY
import com.edurda77.workmaterial.R
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
    private val retrofitPixaImpl: PixabayServiceProvader = PixabayServiceProvader(),
) : ViewModel() {

    fun getNotes(context: Context): LiveData<List<ModelNote>> {
        val roomService = RoomService(context)
        return roomService.getNotesLiveData()
    }
    fun getNote(context: Context, id:Int): LiveData<ModelNote> {
        val roomService = RoomService(context)
        return roomService.getNoteLiveData(id)
    }

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

    fun searchPhotos(
        searchTextView: TextView, inputLayout: TextInputLayout, fragment: Fragment,
        bodySpaceImageView: ImageView, viewModel: DailyImageViewModel
    ) {
        val search = searchTextView.text.toString()
        inputLayout.setEndIconOnClickListener {
            Thread {
                val serverResponseData = viewModel.getPhoto(search)[0]
                val url = serverResponseData.pageURL
                bodySpaceImageView.load(url) {
                    lifecycle(fragment)
                    error(R.drawable.ic_image_error)
                    placeholder(R.drawable.ic_no_photo)
                }
            }.start()

        }
    }

    fun getMarsImageToday(): List<Mars> {
        val currentDate = getDate(1)
        val liveDataForMars: MutableList<Mars> = emptyList<Mars>().toMutableList()
        val images: ImagesMars? = retrofitImpl.getNasaService()
            .getMarsImage(currentDate, NASA_API_KEY).execute().body()
        images?.photos?.forEach {
            liveDataForMars.add(it)
        }
        return liveDataForMars
    }

    fun getEarthImageToday(): List<Earth> {

        val liveDataForEarth: MutableList<Earth> = emptyList<Earth>().toMutableList()
        val images = retrofitImpl.getNasaServiceEpic().getEarthImage().execute().body()
        images?.forEach {
            liveDataForEarth.add(it)
        }
        return liveDataForEarth
    }

    private fun getPhoto(query: String): List<Photo> {
        val liveDataForSearchPhoto: MutableList<Photo> = emptyList<Photo>().toMutableList()
        val images: SearchedPhoto? = retrofitPixaImpl.getPixabayService()
            .getPhoto(PIXABAY_API_KEY, query, LANGUAGE).execute().body()
        images?.hits?.forEach {
            liveDataForSearchPhoto.add(it)
        }
        return liveDataForSearchPhoto
    }

    @SuppressLint("SimpleDateFormat")
    fun getStringFromDate(daysAgo: Int): String {
        val startUrl = "https://epic.gsfc.nasa.gov/archive/natural/"
        val addUrl = "/jpg/"
        val dateFormat: DateFormat = SimpleDateFormat("yyyy/MM/dd")
        return startUrl + dateFormat.format(getTodayDate(daysAgo).time).toString() + addUrl
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(daysAgo: Int): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(getTodayDate(daysAgo).time).toString()
    }

    private fun getTodayDate(daysAgo: Int): Calendar {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -daysAgo)
        return cal
    }

    fun addService(
        title: EditText,
        content: EditText,
        button: Button,
        context: Context
    ) {
        val roomService = RoomService(context)
        button.setOnClickListener {
            val currentTitle = title.text.toString()
            val currentContent = content.text.toString()
            val note = ModelNote(0, currentTitle, currentContent)
            Thread {
                roomService.add(note)
            }.start()
            Toast.makeText(context, "Заметка добавлена", Toast.LENGTH_SHORT).show()

        }

    }

    fun deleteService(
        id: Int,
        button: Button,
        context: Context
    ) {
        val roomService = RoomService(context)
        button.setOnClickListener {
            Thread {
                roomService.delete(id)
            }.start()
            Toast.makeText(context, "Заметка удалена", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateService(
        id: Int,
        titleEditText: EditText,
        contentEditText: EditText,
        button: Button,
        context: Context
    ) {
        val roomService = RoomService(context)
        button.setOnClickListener {
            val content = contentEditText.text.toString()
            val title = titleEditText.text.toString()
            Thread {
                roomService.update(id, title, content)
            }.start()
            Toast.makeText(context, "Заметка обновлена", Toast.LENGTH_SHORT).show()
        }
    }
}


