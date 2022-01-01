package com.edurda77.workmaterial.model

sealed class DailyImage {
    data class Success(val serverResponseData: PODServerResponseData) : DailyImage()

    data class Error(val error: Throwable) : DailyImage()

    data class Loading(val progress: Int?) : DailyImage()

}