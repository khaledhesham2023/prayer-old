package com.khaledamin.prayerapp.data.remote.model.response.prayer

data class Date(
    val gregorian: Gregorian,
    val readable: String,
    val timestamp: String
)