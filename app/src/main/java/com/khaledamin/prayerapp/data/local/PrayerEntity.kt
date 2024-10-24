package com.khaledamin.prayerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayers_table")
data class PrayerEntity(
    @PrimaryKey
    val prayerId: Long?,
    val date:String,
    val day: Int,
    val month: Int,
    val year: Int,
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val latitude: Double,
    val longitude: Double
)
