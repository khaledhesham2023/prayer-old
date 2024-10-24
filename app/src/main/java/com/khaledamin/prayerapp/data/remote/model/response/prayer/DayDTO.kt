package com.khaledamin.prayerapp.data.remote.model.response.prayer

data class DayDTO(
    val date: Date,
    val meta: Meta,
    val timings: Timings
)