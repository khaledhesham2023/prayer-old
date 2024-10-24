package com.khaledamin.prayerapp.utils

import com.khaledamin.prayerapp.data.local.PrayerEntity
import com.khaledamin.prayerapp.data.remote.model.response.prayer.DayDTO
import com.khaledamin.prayerapp.data.remote.model.response.prayer.Timings
import com.khaledamin.prayerapp.data.remote.model.response.qibla.QiblaDTO
import com.khaledamin.prayerapp.domain.model.Day
import com.khaledamin.prayerapp.domain.model.Qibla
import com.khaledamin.prayerapp.domain.model.Timing

fun DayDTO.toDay(): Day {
    return Day(
        prayerId = null,
        day = this.date.gregorian.day.toInt(),
        month = this.date.gregorian.month.numberOfMonth,
        year = this.date.gregorian.year.toInt(),
        latitude = this.meta.method.location.latitude,
        longitude = this.meta.method.location.longitude,
        prayers = this.timings.toPrayersList().toCollection(ArrayList()),
        fajr = convertTimeTo12HrFormat(this.timings.fajr),
        sunrise = convertTimeTo12HrFormat(this.timings.sunrise),
        dhuhr = convertTimeTo12HrFormat(this.timings.dhuhr),
        asr = convertTimeTo12HrFormat(this.timings.asr),
        maghrib = convertTimeTo12HrFormat(this.timings.maghrib),
        isha = convertTimeTo12HrFormat(this.timings.isha),
        date = this.date.readable
    )
}

fun DayDTO.toPrayerEntity(): PrayerEntity {
    return PrayerEntity(
        prayerId = null,
        day = this.date.gregorian.day.toInt(),
        month = this.date.gregorian.month.numberOfMonth,
        year = this.date.gregorian.year.toInt(),
        latitude = this.meta.method.location.latitude,
        longitude = this.meta.method.location.longitude,
        fajr = convertTimeTo12HrFormat(this.timings.fajr),
        sunrise = convertTimeTo12HrFormat(this.timings.sunrise),
        dhuhr = convertTimeTo12HrFormat(this.timings.dhuhr),
        asr = convertTimeTo12HrFormat(this.timings.asr),
        maghrib = convertTimeTo12HrFormat(this.timings.maghrib),
        isha = convertTimeTo12HrFormat(this.timings.isha),
        date = this.date.readable
    )
}

fun PrayerEntity.toDay(): Day {
    return Day(
        prayerId = prayerId,
        day = day,
        month = month,
        year = year,
        latitude = latitude,
        longitude = longitude,
        prayers = ,
        fajr = fajr,
        sunrise = sunrise,
        dhuhr = dhuhr,
        asr = asr,
        maghrib = maghrib,
        isha = isha,
        date = date
    )
}

fun QiblaDTO.toQibla(): Qibla {
    return Qibla(
        direction = this.direction
    )
}

fun Timings.toPrayersList(): List<Timing>{
    return arrayListOf(
        Timing(
            name = Constants.FAJR,
            time = convertTimeTo12HrFormat(this.fajr)
        ),
        Timing(
            name = Constants.SUNRISE,
            time = convertTimeTo12HrFormat(this.sunrise)
        ),
        Timing(
            name = Constants.DHUHR,
            time = convertTimeTo12HrFormat(this.dhuhr)
        ),
        Timing(
            name = Constants.ASR,
            time = convertTimeTo12HrFormat(this.asr)
        ),
        Timing(
            name = Constants.MAGHRIB,
            time = convertTimeTo12HrFormat(this.maghrib)
        ),
        Timing(
            name = Constants.ISHA,
            time = convertTimeTo12HrFormat(this.isha)
        )
    )
}