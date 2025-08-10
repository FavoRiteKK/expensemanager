package com.naveenapps.expensemanager.core.common.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalTime::class)
fun getTodayRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayStartTime = clock.toLocalDateTime(timeZone).date
    val nextDateStartTime = todayStartTime.plus(1, DateTimeUnit.DAY)
    return listOf(
        todayStartTime.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        nextDateStartTime.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

@OptIn(ExperimentalTime::class)
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

@OptIn(ExperimentalTime::class)
fun getThisMonthRange(timeZone: TimeZone = TimeZone.currentSystemDefault()): List<Long> {
    val clock = Clock.System.now()
    val todayDateTime = clock.toLocalDateTime(timeZone)
    val startOfTheWeekDay = todayDateTime.date.minus(todayDateTime.day - 1, DateTimeUnit.DAY)
    val endTimeOfTheWeek = startOfTheWeekDay.plus(1, DateTimeUnit.MONTH)
    return listOf(
        startOfTheWeekDay.atStartOfDayIn(timeZone).toEpochMilliseconds(),
        endTimeOfTheWeek.atStartOfDayIn(timeZone).toEpochMilliseconds()
    )
}

@OptIn(ExperimentalTime::class)
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

@OptIn(ExperimentalTime::class)
fun Long.fromLocalToUTCTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toInstant(TimeZone.UTC)
        .toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun Long.fromUTCToLocalTimeStamp(): Long {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
        .toInstant(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun Long.fromUTCToLocalDate(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
}

@OptIn(ExperimentalTime::class)
fun Long.toExactStartOfTheDay(): LocalDateTime {
    val dateTime = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.UTC)
    return dateTime.date.atStartOfDayIn(TimeZone.currentSystemDefault())
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.getStartOfTheMonth(): Long {
    return this.date.minus(day, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun LocalDateTime.getEndOfTheMonth(): Long {
    val startOfTheWeekDay = this.date.minus(day, DateTimeUnit.DAY)
        .atStartOfDayIn(TimeZone.currentSystemDefault())
    return startOfTheWeekDay.plus(1, DateTimeUnit.MONTH, TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
}

@OptIn(ExperimentalTime::class)
fun Long.toCompleteDate(): LocalDateTime {
    return Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toDate(): String {
    return day.toString().padStart(2, '0')
}

fun LocalDate.toDate(): String {
    return day.toString().padStart(2, '0')
}

fun LocalDateTime.toDateAndMonth(): String {
    val day = day.toString().padStart(2, '0')
    val month = month.number.toString().padStart(2, '0')
    return "$day/$month"
}

fun LocalDateTime.toCompleteDate(): String {
    return LocalDateTime.Format {   //"MMMM dd, yyyy"
        monthName(MonthNames.ENGLISH_FULL)  // FIXME: idk apply to other languages
        char(' ')
        day()
        chars(", ")
        year()
    }.format(this)
}

fun LocalDateTime.toCompleteDateWithDate(): String {
    return LocalDateTime.Format {   //"dd/MM/yyyy"
        day()
        chars("/")
        monthNumber()
        chars("/")
        year()
    }.format(this)
}

@OptIn(ExperimentalTime::class)
fun String.fromCompleteDate(): LocalDate {
    return runCatching {        //"dd/MM/yyyy"
        LocalDate.Format {
            day()
            chars("/")
            monthNumber()
            chars("/")
            year()
        }.parseOrNull(this)
    }.getOrNull() ?: Clock.System.now().asCurrentDateTime().date.also {
        logger.warn { "LocalDateTime.Format failed" }
    }
}

fun LocalDateTime.toMonthAndYear(): String {
    return this.toMonthYear()
}

@OptIn(ExperimentalTime::class)
fun String.fromMonthAndYear(): LocalDateTime? {
    return DateTimeComponents.Format {   //MMMM yyyy
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
    }.parseOrNull(this)
        ?.apply { day = 1 }
        ?.toLocalDate()
        ?.atStartOfDayIn(TimeZone.currentSystemDefault())
        ?.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun LocalDateTime.toMonth(): Int {
    return month.number
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

fun LocalDate.toMonthYear(): String {
    return LocalDate.Format {   //MMMM yyyy
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

fun LocalDate.toDay(): String {
    return LocalDate.Format {   //EEEE
        dayOfWeek(DayOfWeekNames.ENGLISH_FULL)
    }.format(this)
}

@OptIn(ExperimentalTime::class)
fun fromTimeAndHour(hour: Int, minute: Int): LocalTime {
    return runCatching {
        LocalTime(hour, minute)
    }.getOrNull() ?: Clock.System.now().asCurrentDateTime().time.also {
        logger.warn { "LocalTime.Format failed" }
    }
}

fun LocalTime.toTimeAndMinutesWithAMPM(): String {
    return LocalTime.Format {
        //hh:mm a
        this.amPmHour()
        char(':')
        minute()
        char(' ')
        amPmMarker("AM", "PM")
    }.format(this)
}

@OptIn(ExperimentalTime::class)
fun Instant.asCurrentDateTime() =
    this.toLocalDateTime(TimeZone.currentSystemDefault())

@OptIn(ExperimentalTime::class)
fun LocalDateTime.toEpochMilliseconds(timeZone: TimeZone = TimeZone.currentSystemDefault()): Long =
    this.toInstant(timeZone).toEpochMilliseconds()

fun LocalDateTime.lastDayOfMonth(): Int {
    if (month.number == 12) return 31
    return date.plus(1, DateTimeUnit.MONTH)
        .minus(1, DateTimeUnit.DAY)
        .day
}

