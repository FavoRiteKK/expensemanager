package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.transactiontype

import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.model4mp.TransactionType
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository

class UpdateSelectedTransactionTypesUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(categoryTypes: List<TransactionType>?): Resource<Boolean> {
        return settingsRepository.setTransactionTypes(categoryTypes)
    }
}
