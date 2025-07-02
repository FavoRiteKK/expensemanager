package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.DateRangeModel
import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository

class GetDateRangeByTypeUseCase(
    private val dateRangeFilterRepository: DateRangeFilterRepository,
) {
    suspend operator fun invoke(type: DateRangeType): DateRangeModel {
        return dateRangeFilterRepository.getDateRangeFilterTypeString(type)
    }
}
