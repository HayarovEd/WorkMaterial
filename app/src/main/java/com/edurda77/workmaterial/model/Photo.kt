package com.edurda77.workmaterial.model

import com.google.gson.annotations.SerializedName

data class Photo (
    @SerializedName("id")
    val id: Int,
    @SerializedName("pageURL")
    val pageURL: String,
)