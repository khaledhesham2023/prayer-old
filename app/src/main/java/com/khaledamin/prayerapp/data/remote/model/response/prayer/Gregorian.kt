package com.khaledamin.prayerapp.data.remote.model.response.prayer

data class Gregorian(
    val date: String,
    val day: String,
    val format: String,
    val month: Month,
    val weekday: Weekday,
    val year: String
)