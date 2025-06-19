package com.naveenapps.expensemanager.core.common.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date
import java.util.Locale
import kotlin.math.exp
import kotlin.text.padStart

@RunWith(AndroidJUnit4::class)
class DateUtilsTest : BaseCoroutineTest() {

    private fun Long.toExactStartOfTheDay4mp(): LocalDate {
        val dateTime = Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(TimeZone.UTC)
        return dateTime.date.atStartOfDayIn(TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    }

    @Test
    fun getStartOfTheMonth() {
        val esotd = 1750441516000.toExactStartOfTheDay()
        val actual = esotd.getStartOfTheMonth()
        val mpEsotd = 1750441516000.toExactStartOfTheDay4mp()
        val mpDateTime = mpEsotd.atStartOfDayIn(TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val expect = mpDateTime.date.minus(mpDateTime.dayOfMonth, DateTimeUnit.DAY)
            .atStartOfDayIn(TimeZone.currentSystemDefault())
            .toEpochMilliseconds()

        Assert.assertEquals(expect, actual)
    }

    @Test
    fun date_toDate() {
        val actual = Date(1750441516000).toDate()
        val expect = Instant.fromEpochMilliseconds(1750441516000)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .dayOfMonth.toString().padStart(2, '0')

        Assert.assertEquals(expect, actual)
    }

    // Extension function for kotlinx.datetime.LocalDate
    fun LocalDate.toFormattedCompleteDateString(locale: Locale = Locale.getDefault()): String {
        // Get the month name (e.g., "OCTOBER")
        // .name returns the English name in all caps.
        // We'll convert it to title case based on the locale.
        val monthName = this.month.name.lowercase(locale).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(locale) else it.toString()
        }
        val day = this.dayOfMonth
        val year = this.year

        return "$monthName $day, $year"
    }

    @OptIn(FormatStringsInDatetimeFormats::class)
    @Test
    fun toCompleteDate() {
        val expect = Date(1750441516000).toCompleteDate()

        val dateTime = Instant.fromEpochMilliseconds(1750441516000)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val actual = LocalDateTime.Format {
            monthName(MonthNames.ENGLISH_FULL)
            char(' ')
            dayOfMonth()
            chars(", ")
            year()
        }.format(dateTime)

        Assert.assertEquals(expect, actual)
    }
}