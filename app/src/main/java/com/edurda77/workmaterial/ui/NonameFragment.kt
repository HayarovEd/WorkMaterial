package com.edurda77.workmaterial.ui

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.api.load
import com.edurda77.workmaterial.R
import com.edurda77.workmaterial.domain.InterfaceFragment
import com.edurda77.workmaterial.model.DailyImage

open class NonameFragment : InterfaceFragment{
    override fun renderData(dailyImage: DailyImage, fragment:Fragment,
                            context: Context, dailyImageView: ImageView
    ) {

        when (dailyImage) {
            is DailyImage.Success -> {
                val serverResponseData = dailyImage.serverResponseData
                val url = serverResponseData.url

                if (url.isEmpty()) {
                    Toast.makeText(context, "Сегодня фото отсутствует!", Toast.LENGTH_LONG).show()
                } else {
                    dailyImageView.load(url) {
                        lifecycle(fragment)
                        error(R.drawable.ic_image_error)
                        placeholder(R.drawable.ic_no_photo)
                    }
                }
            }
            is DailyImage.Loading -> {

            }
            is DailyImage.Error -> {
                Toast.makeText(context, "Фото не загружено", Toast.LENGTH_LONG).show()
            }
        }
    }



}