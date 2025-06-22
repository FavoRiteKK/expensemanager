package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.Amount
import com.naveenapps.expensemanager.core.model4mp.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun saveCurrency(currency: Currency): Boolean

    fun getDefaultCurrency(): Currency

    fun getSelectedCurrency(): Flow<Currency>

    fun getFormattedCurrency(amount: Amount): Amount
}
