package com.khaledamin.prayerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PrayerEntity::class], version = 1)
abstract class PrayerDatabase : RoomDatabase() {
    abstract fun prayerDao(): PrayerDao
}