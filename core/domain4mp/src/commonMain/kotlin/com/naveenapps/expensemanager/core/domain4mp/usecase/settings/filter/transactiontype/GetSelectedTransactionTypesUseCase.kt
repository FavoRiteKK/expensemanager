package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.transactiontype

import com.naveenapps.expensemanager.core.model4mp.TransactionType
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSelectedTransactionTypesUseCase(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(): Flow<List<TransactionType>> {
        return settingsRepository.getTransactionTypes().map {
            it ?: emptyList()
        }
    }
}
