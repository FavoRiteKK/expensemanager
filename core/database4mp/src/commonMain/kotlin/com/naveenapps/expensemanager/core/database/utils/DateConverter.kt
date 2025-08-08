package com.naveenapps.expensemanager.core.database.utils

import androidx.room.TypeConverter
import com.naveenapps.expensemanager.core.common.utils.fromLocalToUTCTimeStamp
import com.naveenapps.expensemanager.core.common.utils.fromUTCToLocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

object DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.fromUTCToLocalDate()
    }

    @OptIn(ExperimentalTime::class)
    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.currentSystemDefault())
            ?.toEpochMilliseconds()?.fromLocalToUTCTimeStamp()
    }
}
