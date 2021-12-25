package com.edurda77.workmaterial.model

import com.google.gson.annotations.SerializedName

data class ImagesMars(

    @SerializedName("photos")
    val photos: List<Mars>
)