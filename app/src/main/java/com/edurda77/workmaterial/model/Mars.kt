package com.edurda77.workmaterial.model

import com.google.gson.annotations.SerializedName

data class Mars(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sol")
    val sol: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("earth_date")
    val earthDate: String
)