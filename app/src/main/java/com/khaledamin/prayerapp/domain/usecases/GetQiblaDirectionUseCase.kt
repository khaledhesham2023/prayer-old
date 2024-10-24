package com.khaledamin.prayerapp.domain.usecases

import com.khaledamin.prayerapp.domain.repository.PrayerRepo
import javax.inject.Inject

class GetQiblaDirectionUseCase @Inject constructor(
    private val prayerRepo: PrayerRepo
) {
    suspend fun invoke(latitude: Double, longitude: Double) = prayerRepo.getQiblaDirection(latitude, longitude)
}