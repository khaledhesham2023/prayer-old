package com.khaledamin.prayerapp.data.remote.model.response.prayer

import com.google.gson.annotations.SerializedName

data class Month(
    @SerializedName("en")
    val monthInEnglish: String,
    @SerializedName("number")
    val numberOfMonth: Int
)