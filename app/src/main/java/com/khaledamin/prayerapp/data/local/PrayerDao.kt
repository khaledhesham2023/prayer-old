package com.khaledamin.prayerapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PrayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayers(prayerEntities: ArrayList<PrayerEntity>)

    @Query("DELETE FROM prayers_table;")
    suspend fun deleteAllPrayers()

    @Query("SELECT * FROM prayers_table;")
    suspend fun getAllPrayers(): List<PrayerEntity>

    @Query(value = "SELECT * FROM prayers_table where day = :day")
    suspend fun getDay(day:String): PrayerEntity

//    @Query(value = "SELECT * FROM prayers_table where month >= :month AND day >= :day AND latitude = :latitude AND longitude = :longitude AND year = :year LIMIT 7")
//    suspend fun getPrayersForWeek(day:Int,month:Int,latitude:Double,longitude:Double,year: Int): List<PrayerEntity>


}