package com.khaledamin.prayerapp.utils

import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter


fun convertTimeTo12HrFormat(timeString: String): String {
    val actualTime = timeString.substringBefore(" ")

    val inputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm a")

    val timeIn12hrFormat = LocalTime.parse(actualTime, inputFormatter)
    return outputFormatter.format(timeIn12hrFormat)
}