package com.khaledamin.prayerapp.data.remote.model.response.prayer

import com.google.gson.annotations.SerializedName

data class GetPrayerTimesResponse(
    val code: Int,
    @SerializedName("data")
    val daysOfMonth: List<DayDTO>,
    val status: String
)