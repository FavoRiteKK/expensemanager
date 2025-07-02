package com.naveenapps.expensemanager.core.domain4mp.usecase.budget

import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.repository4mp.CurrencyRepository
import com.naveenapps.expensemanager.core.repository4mp.TransactionRepository
import com.naveenapps.expensemanager.core.testing4mp.BaseCoroutineTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.mock.Mocker
import org.kodein.mock.generated.mock
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetBudgetDetailUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val transactionRepository: TransactionRepository = mocker.mock()
    private val currencyRepository: CurrencyRepository = mocker.mock()

    private val getCurrencyUseCase = GetCurrencyUseCase(currencyRepository)
    private val getFormattedAmountUseCase = GetFormattedAmountUseCase(currencyRepository)

    private lateinit var getBudgetDetailUseCase: GetBudgetDetailUseCase

    @Test
    fun whenAllDataAvailableItShouldSendSuccessResult() {
    }
}
