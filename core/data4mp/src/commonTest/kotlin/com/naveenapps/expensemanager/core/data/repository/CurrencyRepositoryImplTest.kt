package com.naveenapps.expensemanager.core.data.repository

import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.deleteDataStoreFile
import com.naveenapps.expensemanager.core.data.testDataStoreModule
import com.naveenapps.expensemanager.core.datastore.di.dataStore
import com.naveenapps.expensemanager.core.model.Amount
import com.naveenapps.expensemanager.core.model.TextFormat
import com.naveenapps.expensemanager.core.model.TextPosition
import com.naveenapps.expensemanager.core.repository.CurrencyRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class CurrencyRepositoryImplTest : BaseCoroutineTest(), KoinTest {

    private val repository: CurrencyRepository by inject()

    private val amount = Amount(
        amount = 100.0,
        amountString = null,
        currency = null,
    )

    private val testScope = TestScope(testCoroutineDispatcher)

    // my modules which override android context and scope in testDataStoreModule
    private val myModules = dataStore + //repository requires this
            testDataStoreModule(scope = testScope) +
            module {
                single<AppCoroutineDispatchers> {   //repository requires this
                    AppCoroutineDispatchers(
                        main = testCoroutineDispatcher,
                        io = testCoroutineDispatcher,
                        computation = testCoroutineDispatcher,
                    )
                }

                singleOf(::CurrencyRepositoryImpl) bind CurrencyRepository::class
            }

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(myModules)
        }
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
        //on desktop-test, clear data manually
        deleteDataStoreFile(testScope)
        testCoroutineDispatcher
        stopKoin()
    }

    @Test
    fun getDefaultCurrencyShouldReturnDefaultCurrency() = runTest {
        val currency = repository.getDefaultCurrency()
        LWTruth_assertThat(currency).isNotNull()
        LWTruth_assertThat(currency).isEqualTo(defaultCurrency)
    }

    @Test
    fun getSelectedCurrencyShouldReturnDefaultCurrency() = runTest {
        repository.getSelectedCurrency().test {
            val currency = awaitItem()
            LWTruth_assertThat(currency).isNotNull()
            LWTruth_assertThat(currency).isEqualTo(defaultCurrency)
        }
    }

    @Test
    fun getSelectedCurrencyShouldReturnAfterUpdate() = runTest {
        repository.getSelectedCurrency().test {
            val currency = awaitItem()
            LWTruth_assertThat(currency).isNotNull()
            LWTruth_assertThat(currency).isEqualTo(defaultCurrency)

            val updatingCurrency = defaultCurrency.copy(
                symbol = "€",
            )

            repository.saveCurrency(updatingCurrency)

            val newCurrency = awaitItem()
            LWTruth_assertThat(newCurrency).isNotNull()
            LWTruth_assertThat(newCurrency).isEqualTo(updatingCurrency)
        }
    }

    @Test
    fun getFormattedAmountWithoutCurrencyShouldReturnAmountWithDefaultCurrency() = runTest {
        val formattedAmount = repository.getFormattedCurrency(amount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(amount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("100.0$")
    }

    @Test
    fun getFormattedAmountWithCurrencyShouldReturnAmountWithPassedCurrency() = runTest {
        val passedAmount =
            amount.copy(amount = 120.0, currency = defaultCurrency.copy(symbol = "€"))
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("120.0€")
    }

    @Test
    fun getFormattedAmountWithPrefix() = runTest {
        val passedAmount = amount.copy(
            amount = 120.0,
            currency = defaultCurrency.copy(
                symbol = "€",
                position = TextPosition.PREFIX,
            ),
        )
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("€120.0")
    }

    @Test
    fun getFormattedAmountWithPrefixAndNumberFormatOne() = runTest {
        val passedAmount = amount.copy(
            amount = 1200.0,
            currency = defaultCurrency.copy(
                symbol = "€",
                position = TextPosition.PREFIX,
                format = TextFormat.NUMBER_FORMAT,
            ),
        )
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("€1,200")
    }

    @Test
    fun getFormattedAmountWithNegativePrefixAndNumberFormatOne() = runTest {
        val passedAmount = amount.copy(
            amount = -1200.0,
            currency = defaultCurrency.copy(
                symbol = "€",
                position = TextPosition.PREFIX,
                format = TextFormat.NUMBER_FORMAT,
            ),
        )
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("-€1,200")
    }

    @Test
    fun getFormattedAmountWithBigNumberAndTextFormatOne() = runTest {
        val passedAmount = amount.copy(
            amount = -1200000000.0,
            currency = defaultCurrency.copy(
                symbol = "€",
                position = TextPosition.PREFIX,
                format = TextFormat.NUMBER_FORMAT,
            ),
        )
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("-€1,200,000,000")
    }

    @Test
    fun getFormattedAmountWithBigNumberAndSuffixTextFormatOne() = runTest {
        val passedAmount = amount.copy(
            amount = -1200000000.44,
            currency = defaultCurrency.copy(
                symbol = "€",
                position = TextPosition.SUFFIX,
                format = TextFormat.NUMBER_FORMAT,
            ),
        )
        val formattedAmount = repository.getFormattedCurrency(passedAmount)
        LWTruth_assertThat(formattedAmount).isNotNull()
        LWTruth_assertThat(formattedAmount.amount).isEqualTo(passedAmount.amount)
        LWTruth_assertThat(formattedAmount.amountString).isEqualTo("-1,200,000,000.4€")
    }
}
