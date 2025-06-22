package com.naveenapps.expensemanager.core.database4mp.utils

import androidx.room.TypeConverter
import com.naveenapps.expensemanager.core.common4mp.utils.fromLocalToUTCTimeStamp
import com.naveenapps.expensemanager.core.common4mp.utils.fromUTCToLocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

object DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.fromUTCToLocalDate()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toInstant(TimeZone.currentSystemDefault())
            ?.toEpochMilliseconds()?.fromLocalToUTCTimeStamp()
    }
}
