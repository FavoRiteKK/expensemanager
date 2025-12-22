package com.naveenapps.expensemanager.core.data.utils

import com.naveenapps.expensemanager.core.data.repository.defaultCurrency
import com.naveenapps.expensemanager.core.model.Currency
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyExtTest : BaseCoroutineTest() {

    @BeforeTest
    fun bef() = super.onCreate()

    @AfterTest
    fun aft() = super.onDestroy()

    @Test
    fun getCurrencyWithDefaultLocale() = runTest {
        //Account(id=1, name=Cash, type=REGULAR, storedIcon=StoredIcon(name=savings, backgroundColor=#4CAF50),
        // createdOn=2025-11-29T09:58:43.370, updatedOn=2025-11-29T09:58:43.370, sequence=2147483647,
        // amount=-90000.0, creditLimit=0.0)
        //AccountUiModel(id=1, name=Cash, storedIcon=StoredIcon(name=savings, backgroundColor=#4CAF50),
        // amount=Amount(amount=-90000.0, amountString=0$, currency=Currency(symbol=$, name=US Dollars,
        // position=SUFFIX, format=NUMBER_FORMAT, namePlural=, nativeSymbol=)), amountTextColor=-769226, type=REGULAR, availableCreditLimit=null)
        val amount = 90_000.0
        val formattedAmount = getCurrency(
            defaultCurrency,
            amount,
        )

        assertNotNull(formattedAmount)
        assertEquals("0.0$", formattedAmount)
    }

    /**
     * Change com.naveenapps.expensemanager.core.common.Platform_androidKt.LWLocale_getDefault
     * to returns Locale.FRANCE before running this test.
     */
    @Test
    fun getCurrencyWithFrenchLocale() = runTest {
        val amount = 1234.5678
        val formattedAmount = getCurrency(
            currency = Currency(
                symbol = "€",
                name = "Euro",
            ),
            amount = amount,
        )

        assertNotNull(formattedAmount)
        // Expected :€1 234,6
        assertEquals("€1 234,6", formattedAmount)
    }
}
