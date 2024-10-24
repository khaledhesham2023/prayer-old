package com.khaledamin.prayerapp.domain.usecases

import com.khaledamin.prayerapp.domain.repository.PrayerRepo
import javax.inject.Inject

class GetDayUsecase @Inject constructor(
    private val prayerRepo: PrayerRepo
) {
    suspend fun invoke(day:String) = prayerRepo.getDay(day)
}