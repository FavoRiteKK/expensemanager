package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.daterange

import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.model4mp.GroupType
import com.naveenapps.expensemanager.core.repository4mp.DateRangeFilterRepository

class GetTransactionGroupTypeUseCase (
    private val dateRangeFilterRepository: DateRangeFilterRepository,
) {

    suspend operator fun invoke(dateRangeType: DateRangeType): GroupType {
        return dateRangeFilterRepository.getTransactionGroupType(dateRangeType)
    }
}
