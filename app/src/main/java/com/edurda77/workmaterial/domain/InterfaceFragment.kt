package com.edurda77.workmaterial.domain

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.edurda77.workmaterial.model.DailyImage

interface InterfaceFragment {
    fun renderData(dailyImage: DailyImage, fragment:Fragment,
                   context:Context, dailyImageView: ImageView
    )
}