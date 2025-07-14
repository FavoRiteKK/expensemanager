package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.DateRangeModel
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository

class GetAllDateRangeUseCase(
    private val dateRangeFilterRepository: DateRangeFilterRepository,
) {
    suspend operator fun invoke(): Resource<List<DateRangeModel>> {
        return dateRangeFilterRepository.getAllDateRanges()
    }
}