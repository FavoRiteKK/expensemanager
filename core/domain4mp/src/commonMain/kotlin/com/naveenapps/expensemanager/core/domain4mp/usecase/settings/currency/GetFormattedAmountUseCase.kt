package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency

import com.naveenapps.expensemanager.core.model4mp.Amount
import com.naveenapps.expensemanager.core.model4mp.Currency
import com.naveenapps.expensemanager.core.repository4mp.CurrencyRepository

class GetFormattedAmountUseCase(private val repository: CurrencyRepository) {
    operator fun invoke(amount: Double, currency: Currency): Amount {
        return repository.getFormattedCurrency(
            Amount(amount = amount, currency = currency),
        )
    }
}
