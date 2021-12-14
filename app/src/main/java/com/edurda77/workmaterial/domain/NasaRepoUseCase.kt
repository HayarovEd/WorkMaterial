package com.edurda77.workmaterial.domain

import androidx.annotation.WorkerThread
import com.edurda77.workmaterial.model.PODServerResponseData

interface NasaRepoUseCase {
    @WorkerThread
    @Throws(Throwable::class)
    fun getFotoOfTheDaySync(): PODServerResponseData?
    fun getFotoOfTheDayAsync (onSuccess: (PODServerResponseData?)->Unit,
                                     OnError: (Throwable) ->Unit)
}