package com.khaledamin.prayerapp.data.remote.model.response.prayer

data class Meta(
    val latitude: Double,
    val longitude: Double,
    val method: Method,
    val timezone: String
)