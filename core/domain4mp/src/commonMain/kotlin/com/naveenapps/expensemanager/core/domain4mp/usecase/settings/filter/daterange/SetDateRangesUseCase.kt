package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository
import kotlinx.datetime.LocalDateTime

class SetDateRangesUseCase(
    private val dateRangeFilterRepository: DateRangeFilterRepository,
) {

    suspend operator fun invoke(customDateRange: List<LocalDateTime>): Resource<Boolean> {
        return dateRangeFilterRepository.setDateRanges(customDateRange)
    }
}