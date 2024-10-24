package com.khaledamin.prayerapp.data.remote

import com.khaledamin.prayerapp.data.remote.model.response.prayer.GetPrayerTimesResponse
import com.khaledamin.prayerapp.data.remote.model.response.qibla.GetQiblaDirectionResponse
import com.khaledamin.prayerapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PrayerApi {

    @GET("calendar/{year}/{month}")
    suspend fun getPrayerTimes(
        @Path("year") year: Int,
        @Path("month") month:Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int = Constants.PRAYER_METHOD
    ): GetPrayerTimesResponse

    @GET("qibla/{latitude}/{longitude}")
    suspend fun getQiblaDirection(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): GetQiblaDirectionResponse
}