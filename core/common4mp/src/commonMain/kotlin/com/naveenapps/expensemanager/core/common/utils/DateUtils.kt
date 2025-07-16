package com.naveenapps.expensemanager.core.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun getTodayRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayStartTime = clock.toLocalDateTime(timeZone).date
    val nextDateStartTime = todayStartTime.plus(1, DateTimeUnit.DAY)
    return listOf(
        todayStartTime.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        nextDateStartTime.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

fun getThisWeekRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayDateTime = clock.toLocalDateTime(timeZone)
    val startOfTheWeekDay =
        todayDateTime.date.minus(todayDateTime.dayOfWeek.isoDayNumber, DateTimeUnit.DAY)
    val endTimeOfTheWeek = startOfTheWeekDay.plus(1, DateTimeUnit.WEEK)
    return listOf(
        startOfTheWeekDay.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        endTimeOfTheWeek.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

fun getThisMonthRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayDateTime = clock.toLocalDateTime(timeZone)
    val startOfTheWeekDay = todayDateTime.date.minus(todayDateTime.dayOfMonth - 1, DateTimeUnit.DAY)
    val endTimeOfTheWeek = startOfTheWeekDay.plus(1, DateTimeUnit.MONTH)
    return listOf(
        startOfTheWeekDay.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        endTimeOfTheWeek.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

fun getThisYearRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayDateTime = clock.toLocalDateTime(timeZone)
    val startOfTheWeekDay = todayDateTime.date.minus(todayDateTime.dayOfYear - 1, DateTimeUnit.DAY)
    val endTimeOfTheWeek = startOfTheWeekDay.plus(1, DateTimeUnit.YEAR)
    return listOf(
        startOfTheWeekDay.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        endTimeOfTheWeek.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

fun Long.fromLocalToUTCTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

fun Long.fromUTCToLocalTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

fun Long.fromUTCToLocalDate(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
}

fun Long.toExactStartOfTheDay(): LocalDateTime {
    val dateTime = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
    return dateTime.date.atStartOfDayIn(TimeZone.currentSystemDefault())
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.getStartOfTheMonth(): Long {
    return this.date.minus(this.dayOfMonth, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

fun LocalDateTime.getEndOfTheMonth(): Long {
    val startOfTheWeekDay = this.date.minus(this.dayOfMonth, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
    return startOfTheWeekDay.plus(1, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

fun Long.toCompleteDate(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toDate(): String {
    return this.dayOfMonth.toString().padStart(2, '0')
}

fun LocalDateTime.toDateAndMonth(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    return "$day/$month"
}

fun LocalDateTime.toCompleteDate(): String {
    return LocalDateTime.Format {   //"MMMM dd, yyyy"
        monthName(MonthNames.ENGLISH_FULL)  // FIXME: idk apply to other languages
        char(' ')
        dayOfMonth()
        chars(", ")
        year()
    }.format(this)
}

fun LocalDateTime.toCompleteDateWithDate(): String {
    return LocalDateTime.Format {   //"dd/MM/yyyy"
        dayOfMonth()
        chars("/")
        monthNumber()
        chars("/")
        year()
    }.format(this)
}

fun String.fromCompleteDate(): LocalDateTime {
    return runCatching {        //"dd/MM/yyyy"
        LocalDateTime.Format {
            dayOfMonth()
            chars("/")
            monthNumber()
            chars("/")
            year()
        }.parseOrNull(this)
    }.getOrNull() ?: Clock.System.now().asCurrentDateTime()
}

fun LocalDateTime.toMonthAndYear(): String {
    return this.toMonthYear()
}

fun String.fromMonthAndYear(): LocalDateTime? {
    return DateTimeComponents.Format {   //MMMM yyyy
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
    }.parseOrNull(this)
        ?.apply { dayOfMonth = 1 }
        ?.toLocalDate()
        ?.atStartOfDayIn(TimeZone.currentSystemDefault())
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toMonth(): Int {
    return this.monthNumber
}

fun LocalDateTime.toYear(): String {
    return LocalDateTime.Format {   //yyyy
        this.year()
    }.format(this)
}

fun LocalDateTime.toYearInt(): Int {
    return this.toYear().toInt()
}

fun LocalDateTime.toTimeAndMinutes(): String {
    return LocalDateTime.Format {   //HH:mm
        hour()
        char(':')
        minute()
    }.format(this)
}

fun LocalDateTime.toMonthYear(): String {
    return LocalDateTime.Format {   //MMMM yyyy
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
    }.format(this)
}

fun LocalDateTime.toDay(): String {
    return LocalDateTime.Format {   //EEEE
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    }.format(this)
}

fun String.fromTimeAndHour(): LocalDateTime {
    return runCatching {
        LocalDateTime.Format {  //HH:mm
            hour()
            char(':')
            minute()
        }.parseOrNull(this)
    }.getOrNull() ?: Clock.System.now().asCurrentDateTime()
}

fun LocalDateTime.toTimeAndMinutesWithAMPM(): String {
    return LocalDateTime.Format {
        //hh:mm a
        this.amPmHour()
        char(':')
        minute()
        char(' ')
        amPmMarker("AM", "PM")
    }.format(this)
}

fun Instant.asCurrentDateTime() =
    this.toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDateTime.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long =
    this.toInstant(timeZone).toEpochMilliseconds()
