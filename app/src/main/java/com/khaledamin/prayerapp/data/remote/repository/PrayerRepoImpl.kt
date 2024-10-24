package com.khaledamin.prayerapp.data.remote.repository

import com.khaledamin.prayerapp.data.local.PrayerDao
import com.khaledamin.prayerapp.data.local.PrayerEntity
import com.khaledamin.prayerapp.data.remote.PrayerApi
import com.khaledamin.prayerapp.data.remote.model.response.prayer.DayDTO
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.domain.repository.PrayerRepo
import com.khaledamin.prayerapp.utils.NetworkState
import com.khaledamin.prayerapp.utils.State
import com.khaledamin.prayerapp.utils.toDay
import javax.inject.Inject

class PrayerRepoImpl @Inject constructor(
    private val api: PrayerApi,
    private val dao: PrayerDao,
    private val networkState: NetworkState,
) : PrayerRepo {
    override suspend fun getPrayersFromNetwork(
        year: Int,
        month: Int,
        latitude: Double,
        longitude: Double,
    ): ArrayList<DayDTO> {
        return api.getPrayerTimes(
            year = year,
            month = month,
            latitude = latitude,
            longitude = longitude
        ).daysOfMonth.toCollection(ArrayList())
    }

    override suspend fun getPrayersFromLocal(): ArrayList<Day> {
        return dao.getAllPrayers().map { prayerEntity -> prayerEntity.toDay() }.toCollection(ArrayList())
    }

    override suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>) {
        dao.insertPrayers(prayers)
    }

//    override suspend fun getPrayersFromLocal(context: Context): Flow<State<ArrayList<Day>>> {
//        return flow {
//            val prayerTimes = try {
//                dao.getAllPrayers()
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//                emit(State.Error(message = exception.message))
//                return@flow
//            }
//            if (prayerTimes.isEmpty()) {
//                emit(State.Error(message = context.getString(R.string.no_cached_data_found)))
//                return@flow
//            }
//            emit(State.Success(data = prayerTimes.map { prayerEntity ->
//                prayerEntity.toDay()
//            }.toCollection(ArrayList())))
//        }
//
//    }

//    override suspend fun getPrayers(
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//        context: Context,
//    ): Flow<State<ArrayList<Day>>> {
//        return flow {
//            val prayers = try {
//                if (networkState.isInternetAvailable() || dao.getAllPrayers().isEmpty()) {
//                    val remoteData =
//                        api.getPrayerTimes(year, month, latitude, longitude).daysOfMonth
//                    dao.deleteAllPrayers()
//                    dao.insertPrayers(remoteData.map { dayDTO ->
//                        dayDTO.toPrayerEntity()
//                    }.toCollection(ArrayList()))
//                    dao.getAllPrayers()
//                } else if (!networkState.isInternetAvailable() && dao.getAllPrayers().isEmpty()) {
//                    emit(State.Error(message = context.getString(R.string.no_cached_data_found)))
//                    return@flow
//                } else {
//                    dao.getAllPrayers()
//                }
//            } catch (httpException: HttpException) {
//                httpException.printStackTrace()
//                emit(State.Error(message = httpException.message))
//                return@flow
//            } catch (ioException: IOException) {
//                ioException.printStackTrace()
//                emit(State.Error(message = ioException.message))
//                return@flow
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//                emit(State.Error(message = exception.message))
//                return@flow
//            }
//            emit(State.Success(data = prayers.map { prayerEntity ->
//                prayerEntity.toDay()
//            }.toCollection(ArrayList())))
//        }
//    }

//    override suspend fun getDay(day: String): Flow<State<Day>> {
//        return flow {
//            emit(State.Loading())
//            val response = try {
//                dao.getDay(day)
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//                emit(State.Error(message = exception.message))
//                return@flow
//            }
//            emit(State.Success(data = response.toDay()))
//        }
//    }

//    override suspend fun getQiblaDirection(
//        latitude: Double,
//        longitude: Double,
//    ): Flow<State<Qibla>> {
//        return flow {
//            emit(State.Loading())
//            val response = try {
//                api.getQiblaDirection(latitude = latitude, longitude = longitude)
//            } catch (httpException: HttpException) {
//                httpException.printStackTrace()
//                emit(State.Error(message = httpException.message!!))
//                return@flow
//            } catch (ioException: IOException) {
//                ioException.printStackTrace()
//                emit(State.Error(message = ioException.message))
//                return@flow
//            } catch (exception: Exception) {
//                exception.printStackTrace()
//                emit(State.Error(message = exception.message))
//                return@flow
//            }
//            emit(State.Success(data = response.qibla.toQibla()))
//        }
//    }

//    override suspend fun getPrayersForWeek(
//        day: Int,
//        year: Int,
//        month: Int,
//        latitude: Double,
//        longitude: Double,
//    ): ArrayList<Day> {
//        return dao.getPrayersForWeek(day, month, latitude, longitude, year).map { prayerEntity -> prayerEntity.toDay() }
//            .toCollection(ArrayList())
//    }

//    override suspend fun getAllPrayers(): ArrayList<PrayerEntity> {
//        return dao.getAllPrayers().toCollection(ArrayList())
//    }

//    override suspend fun insertPrayers(prayers: ArrayList<PrayerEntity>) {
//        dao.insertPrayers(prayers)
//    }
}