package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency

import com.naveenapps.expensemanager.core.model4mp.Currency
import com.naveenapps.expensemanager.core.repository4mp.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetCurrencyUseCase(private val repository: CurrencyRepository) {
    operator fun invoke(): Flow<Currency> {
        return repository.getSelectedCurrency()
    }
}
