package com.naveenapps.expensemanager.core.data4mp.repository

import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data4mp.utils.getCurrency
import com.naveenapps.expensemanager.core.datastore4mp.CurrencyDataStore
import com.naveenapps.expensemanager.core.model4mp.Amount
import com.naveenapps.expensemanager.core.model4mp.Currency
import com.naveenapps.expensemanager.core.model4mp.TextFormat
import com.naveenapps.expensemanager.core.model4mp.TextPosition
import com.naveenapps.expensemanager.core.model4mp.isPrefix
import com.naveenapps.expensemanager.core.repository4mp.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val MINUS_SYMBOL = "-"
private const val DEFAULT_CURRENCY_SYMBOL = "$"
private const val DEFAULT_CURRENCY_NAME = "US Dollars"

val defaultCurrency = Currency(
    name = DEFAULT_CURRENCY_NAME,
    symbol = DEFAULT_CURRENCY_SYMBOL,
    position = TextPosition.SUFFIX,
    format = TextFormat.NONE,
)

class CurrencyRepositoryImpl(
    private val dataStore: CurrencyDataStore,
    private val dispatchers: AppCoroutineDispatchers,
) : CurrencyRepository {

    override fun getDefaultCurrency(): Currency {
        return defaultCurrency
    }

    override suspend fun saveCurrency(currency: Currency): Boolean = withContext(dispatchers.io) {
        dataStore.setCurrency(
            name = currency.name,
            symbol = currency.symbol,
            position = currency.position.ordinal,
            format = currency.format.ordinal,
        )
        true
    }

    override fun getSelectedCurrency(): Flow<Currency> {
        return dataStore.getCurrency(defaultCurrency = getDefaultCurrency())
    }

    override fun getFormattedCurrency(amount: Amount): Amount {
        val currency = amount.currency ?: getDefaultCurrency()
        return amount.copy(
            amountString = getCurrency(
                currency = currency,
                amount = amount.amount,
            ).let {
                return@let if (currency.position.isPrefix() && it.contains(MINUS_SYMBOL)) {
                    "${MINUS_SYMBOL}${it.replace(MINUS_SYMBOL, "")}"
                } else {
                    it
                }
            },
        )
    }
}
