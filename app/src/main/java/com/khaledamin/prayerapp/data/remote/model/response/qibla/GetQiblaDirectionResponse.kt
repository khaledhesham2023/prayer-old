package com.khaledamin.prayerapp.data.remote.model.response.qibla

import com.google.gson.annotations.SerializedName

data class GetQiblaDirectionResponse(
    val code: Int,
    @SerializedName("data")
    val qibla: QiblaDTO,
    val status: String
)