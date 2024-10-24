//package com.khaledamin.prayerapp.domain.usecases
//
//import android.content.Context
//import android.util.Log
//import com.khaledamin.prayerapp.data.local.PrayerDao
//import com.khaledamin.prayerapp.domain.model.Day
//import com.khaledamin.prayerapp.domain.repository.PrayerRepo
//import com.khaledamin.prayerapp.utils.NetworkState
//import com.khaledamin.prayerapp.utils.toDay
//import com.khaledamin.prayerapp.utils.toPrayerEntity
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//
//class GetPrayerTimesUseCase @Inject constructor(
//    private val prayerRepo: PrayerRepo,
//    private val dao: PrayerDao,
//    private val networkState: NetworkState,
//    @ApplicationContext private val context: Context,
//) {
//        suspend fun invoke(
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//        context: Context
//    ) : ArrayList<Day>{
//        return if (networkState.isInternetAvailable()) prayerRepo.getPrayersFromNetwork(year, month, latitude, longitude).map {
//            it.toDay()
//        }.toCollection(ArrayList()) else prayerRepo.getPrayersFromLocal()
//    }
////    suspend fun invoke(
////        day: Int,
////        year: Int,
////        month: Int,
////        latitude: Double,
////        longitude: Double,
////    ): ArrayList<Day> {
////        val weekDays = prayerRepo.getPrayersForWeek(day, year, month, latitude, longitude)
////        Log.i("TAGGG",prayerRepo.getAllPrayers()[0].year.toString())
////            when {
////                weekDays.isEmpty() -> {
////                    dao.insertPrayers(
////                        prayerRepo.getPrayersFromNetwork(year, month, latitude, longitude)
////                            .map { dayDTO ->
////                                dayDTO.toPrayerEntity()
////                            }.toCollection(ArrayList())
////                    )
////                    return weekDays
////                }
////
////                prayerRepo.getPrayersForWeek(day, year, month, latitude, longitude).size < 7 -> {
////                    dao.insertPrayers(
////                        prayerRepo.getPrayersFromNetwork(year, month + 1, latitude, longitude)
////                            .map { dayDTO -> dayDTO.toPrayerEntity() }.toCollection(ArrayList())
////                    )
////                    return dao.getPrayersForWeek(day, month, latitude, longitude, year)
////                        .map { prayerEntity -> prayerEntity.toDay() }.toCollection(ArrayList())
////                }
////
////                else -> {
////                    return prayerRepo.getPrayersForWeek(day, year, month, latitude, longitude)
////                }
////            }
////        return when {
////            weekDays.isEmpty() -> {
////                val remoteWeekDays =
////                    prayerRepo.getPrayersFromNetwork(year, month, latitude, longitude)
////                        .map { dayDTO -> dayDTO.toPrayerEntity() }.toCollection(ArrayList())
//////                remoteWeekDays.forEach { Log.i("TAGGG", "Inserting day: ${it.date}") }
////                Log.i("TAGGG", remoteWeekDays[0].date)
////                dao.insertPrayers(remoteWeekDays)
////                dao.getPrayersForWeek(day, month, latitude, longitude, year).map { prayerEntity -> prayerEntity.toDay() }.toCollection(ArrayList())
////            }
////
////            weekDays.size < 7 -> {
////                Log.i("TAGGG", "less than 7 condition met")
////                val remoteWeekDays =
////                    prayerRepo.getPrayersFromNetwork(year, month + 1, latitude, longitude)
////                dao.insertPrayers(remoteWeekDays.map { dayDTO -> dayDTO.toPrayerEntity() }
////                    .toCollection(ArrayList()))
////                prayerRepo.getPrayersForWeek(day, year, month, latitude, longitude)
////            }
////
////            else -> {
////                Log.i("TAGGG", "All fit condition met")
////
////                weekDays
////            }
//
//    }
//}