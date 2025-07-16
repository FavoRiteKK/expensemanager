package com.naveenapps.expensemanager.core.data.utils

import com.naveenapps.expensemanager.core.data.repository.defaultCurrency
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
        val amount = 0.0
        val formattedAmount = getCurrency(
            defaultCurrency,
            amount,
        )

        assertNotNull(formattedAmount)
        assertEquals("0.0$", formattedAmount)
    }
}
