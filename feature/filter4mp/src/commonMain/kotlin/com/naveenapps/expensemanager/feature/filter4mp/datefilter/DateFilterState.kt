package com.naveenapps.expensemanager.feature.filter4mp.datefilter

import com.naveenapps.expensemanager.core.model4mp.DateRangeModel
import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.model4mp.TextFieldValue
import kotlinx.datetime.LocalDateTime

data class DateFilterState(
    val dateRangeType: TextFieldValue<DateRangeType>,
    val fromDate: TextFieldValue<LocalDateTime>,
    val toDate: TextFieldValue<LocalDateTime>,
    val dateRangeTypeList: List<DateRangeModel>,
    val showCustomRangeSelection: Boolean,
    val showDateFilter: Boolean,
    val dateFilterType: DateFilterType
)