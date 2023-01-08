package com.caesar84mx.swensonforecast.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDateTime.formatted(pattern: String): String {
    val format = DateTimeFormatter.ofPattern(pattern, Locale.US)
    return format(format)
}

fun LocalDate.isToday(): Boolean {
    val dayEpoch = toEpochDay()
    val todayEpoch = LocalDate.now().toEpochDay()
    return dayEpoch == todayEpoch
}

fun LocalDate.isTomorrow(): Boolean {
    val todayYear = LocalDate.now().year
    val today = LocalDate.now().dayOfYear
    return year == todayYear && dayOfYear - today == 1
}

fun LocalDate.toRelativeDay(): String {
    if (isToday()) {
        return "Today"
    }
    if (isTomorrow()) {
        return "Tomorrow"
    }

    return atStartOfDay().formatted("EEEE")
}

operator fun String.get(start: Int, end: Int): String = substring(start, endIndex = end)