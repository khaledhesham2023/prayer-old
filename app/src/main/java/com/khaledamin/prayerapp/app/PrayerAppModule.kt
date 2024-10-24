package com.khaledamin.prayerapp.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.khaledamin.prayerapp.BuildConfig
import com.khaledamin.prayerapp.data.local.PrayerDao
import com.khaledamin.prayerapp.data.local.PrayerDatabase
import com.khaledamin.prayerapp.data.remote.PrayerApi
import com.khaledamin.prayerapp.data.remote.repository.PrayerRepoImpl
import com.khaledamin.prayerapp.domain.repository.PrayerRepo
import com.khaledamin.prayerapp.utils.Constants
import com.khaledamin.prayerapp.utils.NetworkState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PrayerAppModule : Application() {

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gsonConverterFactory: GsonConverterFactory): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory).build()

    @Provides
    @Singleton
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providePrayerDatabase(@ApplicationContext context: Context): PrayerDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            PrayerDatabase::class.java,
            BuildConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providePrayerDao(prayerDatabase: PrayerDatabase): PrayerDao = prayerDatabase.prayerDao()

    @Provides
    @Singleton
    fun providePrayerApi(retrofit: Retrofit): PrayerApi = retrofit.create(PrayerApi::class.java)

    @Provides
    @Singleton
    fun providePrayerRepo(
        prayerApi: PrayerApi,
        prayerDao: PrayerDao,
        networkState: NetworkState,
    ): PrayerRepo =
        PrayerRepoImpl(prayerApi, prayerDao, networkState)

    @Provides
    @Singleton
    fun provideNetworkState(@ApplicationContext context: Context): NetworkState =
        NetworkState(context)
}