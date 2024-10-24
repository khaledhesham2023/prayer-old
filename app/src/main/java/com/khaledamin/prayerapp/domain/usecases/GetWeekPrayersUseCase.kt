package com.khaledamin.prayerapp.domain.usecases

import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.domain.repository.PrayerRepo
import com.khaledamin.prayerapp.utils.NetworkState
import com.khaledamin.prayerapp.utils.toPrayerEntity
import javax.inject.Inject


class GetWeekPrayersUseCase @Inject constructor(
    private val prayerRepo: PrayerRepo,
    private val networkState: NetworkState,
) {

    suspend fun getWeeklyPrayers(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
    ) : ArrayList<Day> {
        if (networkState.isInternetAvailable()) {
            prayerRepo.insertPrayers(
                prayerRepo.getPrayersFromNetwork(
                    year,
                    month,
                    latitude,
                    longitude
                ).map { dayDTO -> dayDTO.toPrayerEntity() }.toCollection(ArrayList())
            )
            return prayerRepo.getPrayersFromLocal()
        } else {
            return prayerRepo.getPrayersFromLocal()
        }
    }
//    suspend fun getWeekPrayers(
//        day: Int,
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//    ): Flow<State<ArrayList<Day>>> {
//        val daysList = dao.getPrayersForWeek(day, month, latitude, longitude,year)
//        when {
//            daysList.isEmpty() -> {
//                prayerRepo.getPrayersFromNetwork(year, month, latitude, longitude)
//            }
//        }
//    }
}