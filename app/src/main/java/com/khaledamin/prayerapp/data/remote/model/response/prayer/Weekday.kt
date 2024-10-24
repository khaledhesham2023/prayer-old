package com.khaledamin.prayerapp.data.remote.model.response.prayer

import com.google.gson.annotations.SerializedName

data class Weekday(
    @SerializedName("en")
    val dayInEnglish: String
)