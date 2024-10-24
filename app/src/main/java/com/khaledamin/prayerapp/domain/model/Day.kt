package com.khaledamin.prayerapp.domain.model

data class Day(
    val prayerId:Long?,
    val day: Int,
    val month: Int,
    val year: Int,
    val prayers : ArrayList<Timing>,
    val latitude: Double,
    val longitude: Double,
    val date: String
)