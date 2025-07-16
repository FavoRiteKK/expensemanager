package com.naveenapps.expensemanager.core.domain.usecase.settings.currency

import com.naveenapps.expensemanager.core.model.Currency
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.CurrencyRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
@UsesMocks(CurrencyRepository::class)
class SaveCurrencyUseCaseTest : BaseCoroutineTest() {

    private val currency = Currency(
        symbol = "$",
        name = "US Dollars",
    )

    private val mocker = Mocker()
    private val currencyRepository: CurrencyRepository by lazyOf(mocker.mock())
    private lateinit var saveCurrencyUseCase: SaveCurrencyUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        saveCurrencyUseCase = SaveCurrencyUseCase(currencyRepository)
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenValidCurrencyReturnSuccess() = runTest {
        mocker.everySuspending { currencyRepository.saveCurrency(currency) } returns true

        val response = saveCurrencyUseCase.invoke(currency)

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun currencyNameIsInvalidReturnError() = runTest {
        val response = saveCurrencyUseCase.invoke(currency.copy(symbol = ""))

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }

    @Test
    fun currencySymbolIsInvalidReturnError() = runTest {
        val response = saveCurrencyUseCase.invoke(currency.copy(name = ""))

        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Error::class)
        val exception = (response as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
        LWTruth_assertThat(exception.message).isNotEmpty()
    }
}
