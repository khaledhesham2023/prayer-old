package com.khaledamin.prayerapp.domain.repository

import com.khaledamin.prayerapp.data.local.PrayerEntity
import com.khaledamin.prayerapp.data.remote.model.response.prayer.DayDTO
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.utils.State

interface PrayerRepo {

    suspend fun getPrayersFromNetwork(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
    ): ArrayList<DayDTO>

    suspend fun getPrayersFromLocal(): ArrayList<Day>

    suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>)

//    suspend fun getPrayers(
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//        context: Context,
//    ): Flow<State<ArrayList<Day>>>
//
//    suspend fun getDay(day: String): Flow<State<Day>>
//
//    suspend fun getQiblaDirection(
//        latitude: Double,
//        longitude: Double,
//    ): Flow<State<Qibla>>
//
//    suspend fun getPrayersForWeek(
//        day: Int,
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//    ): ArrayList<Day>

//    suspend fun getAllPrayers(): ArrayList<PrayerEntity>
////    suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>)
}