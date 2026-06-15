package com.naveenapps.expensemanager.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

data class DateRangeModel(
    val name: String,
    val description: String,
    val type: DateRangeType,
    val dateRanges: List<Long>,
)

val DateRangeModel.includeToday: Boolean
    get() {
        val start = dateRanges.first()
        val end = when (type) {
            DateRangeType.CUSTOM -> {
                Instant.fromEpochMilliseconds(dateRanges.last())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date.plus(1, DateTimeUnit.DAY)
                    .atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
            }

            else -> {
                dateRanges.last()
            }
        }
        return Clock.System.now().toEpochMilliseconds() in start..<end
    }
