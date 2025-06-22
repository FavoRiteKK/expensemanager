package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.DateRangeModel
import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.model4mp.GroupType
import com.naveenapps.expensemanager.core.model4mp.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

interface DateRangeFilterRepository {

    suspend fun getAllDateRanges(): Resource<List<DateRangeModel>>

    fun getDateRangeFilterType(): Flow<DateRangeType>

    fun getDateRangeTimeFrame(): Flow<List<Long>?>

    suspend fun setDateRangeFilterType(dateRangeType: DateRangeType): Resource<Boolean>

    suspend fun getDateRangeFilterRangeName(dateRangeType: DateRangeType): String

    suspend fun getDateRangeFilterTypeString(dateRangeType: DateRangeType): DateRangeModel

    suspend fun setDateRanges(dateRanges: List<LocalDateTime>): Resource<Boolean>

    suspend fun getTransactionGroupType(dateRangeType: DateRangeType): GroupType
}
