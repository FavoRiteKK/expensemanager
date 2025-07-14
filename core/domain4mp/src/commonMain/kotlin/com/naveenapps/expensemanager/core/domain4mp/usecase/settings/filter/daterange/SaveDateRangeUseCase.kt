package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository
import kotlinx.datetime.LocalDateTime

class SaveDateRangeUseCase(
    private val dateRangeFilterRepository: DateRangeFilterRepository,
    private val setDateRangesUseCase: SetDateRangesUseCase,
) {

    suspend operator fun invoke(
        dateRangeType: DateRangeType,
        customRanges: List<LocalDateTime>,
    ): Resource<Boolean> {
        return when (val response = setDateRangesUseCase.invoke(customRanges)) {
            is Resource.Error -> {
                response
            }

            is Resource.Success -> {
                dateRangeFilterRepository.setDateRangeFilterType(dateRangeType)
            }
        }
    }
}
