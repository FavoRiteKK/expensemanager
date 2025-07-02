package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.DateRangeModel
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDateRangeUseCase(
    private val getDateRangeByTypeUseCase: GetDateRangeByTypeUseCase,
    private val dateRangeFilterRepository: DateRangeFilterRepository,
) {

    operator fun invoke(): Flow<DateRangeModel> {
        return combine(
            dateRangeFilterRepository.getDateRangeFilterType(),
            dateRangeFilterRepository.getDateRangeTimeFrame(),
        ) { type, _ ->
            getDateRangeByTypeUseCase.invoke(type)
        }
    }
}
