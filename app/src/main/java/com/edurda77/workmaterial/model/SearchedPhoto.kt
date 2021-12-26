package com.edurda77.workmaterial.model

import com.google.gson.annotations.SerializedName

data class SearchedPhoto (
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
    @SerializedName("hits")
    val hits: List<Photo>
)