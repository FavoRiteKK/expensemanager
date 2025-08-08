package com.naveenapps.expensemanager.core.data.repository

import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.deleteDataStoreFile
import com.naveenapps.expensemanager.core.data.testDataStoreModule
import com.naveenapps.expensemanager.core.datastore.di.dataStore
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.repository.SettingsRepository
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

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest : BaseCoroutineTest(), KoinTest {
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)
    // my modules which override android context and scope in testDataStoreModule
    private val myModules = dataStore + //repository requires this
            testDataStoreModule(scope = testCoroutineScope) +
            module {
                single<AppCoroutineDispatchers> {   //repository requires this
                    AppCoroutineDispatchers(
                        main = testCoroutineDispatcher,
                        io = testCoroutineDispatcher,
                        computation = testCoroutineDispatcher,
                    )
                }

                singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
            }
    private val repository: SettingsRepository by inject()

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
        deleteDataStoreFile(testCoroutineScope)
        testCoroutineDispatcher
        stopKoin()
    }

    @Test
    fun `when getTransactionTypes for first time should return null`() = runTest {
        repository.getTransactionTypes().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isEmpty()
        }
    }

    @Test
    fun `when setTransactionTypes with account id should return the success`() = runTest {
        val transactionTypes =
            listOf(TransactionType.EXPENSE, TransactionType.INCOME, TransactionType.TRANSFER)
        val response = repository.setTransactionTypes(transactionTypes)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getTransactionTypes after saving the item should return the saved value`() = runTest {
        val transactionTypes =
            listOf(TransactionType.EXPENSE, TransactionType.INCOME, TransactionType.TRANSFER)

        repository.setTransactionTypes(transactionTypes)

        repository.getTransactionTypes().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isNotEmpty()
            LWTruth_assertThat(item).isEqualTo(transactionTypes)
        }
    }

    @Test
    fun `when getAccounts for first time should return null`() = runTest {
        repository.getAccounts().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isEmpty()
        }
    }

    @Test
    fun `when setAccounts with account id should return the success`() = runTest {
        val accounts = listOf("sampleId")
        val response = repository.setAccounts(accounts)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getAccounts after saving the item should return the saved value`() = runTest {
        val accounts = listOf("sampleId")
        repository.setAccounts(accounts)
        repository.getAccounts().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isNotEmpty()
            LWTruth_assertThat(item).isEqualTo(accounts)
        }
    }

    @Test
    fun `when getCategories for first time should return null`() = runTest {
        repository.getCategories().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isEmpty()
        }
    }

    @Test
    fun `when setCategories with account id should return the success`() = runTest {
        val categories = listOf("sampleId")
        val response = repository.setCategories(categories)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getCategories after saving the item should return the saved value`() = runTest {
        val categories = listOf("sampleId")
        repository.setCategories(categories)
        repository.getCategories().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isNotEmpty()
            LWTruth_assertThat(item).isEqualTo(categories)
        }
    }

    @Test
    fun `when isPreloaded for first time should return false`() = runTest {
        repository.isPreloaded().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isFalse()
        }
    }

    @Test
    fun `when isPreloaded after saving the item should return the saved value`() = runTest {
        repository.setPreloaded(true)
        repository.isPreloaded().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isTrue()
        }
    }

    @Test
    fun `when setPreloaded with account id should return the success`() = runTest {
        val response = repository.setPreloaded(true)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getDefaultAccount for first time should return null`() = runTest {
        repository.getDefaultAccount().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNull()
        }
    }

    @Test
    fun `when setDefaultAccount with account id should return the success`() = runTest {
        val response = repository.setDefaultAccount("sampleId")
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getDefaultAccount after saving the item should return the saved value`() = runTest {
        val expectedItem = "sampleId"

        repository.setDefaultAccount(expectedItem)

        repository.getDefaultAccount().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNotNull()
            LWTruth_assertThat(item).isNotEmpty()
            LWTruth_assertThat(item).isEqualTo(expectedItem)
        }
    }

    @Test
    fun `when getDefaultExpenseCategory for first time should return null`() = runTest {
        repository.getDefaultExpenseCategory().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNull()
        }
    }

    @Test
    fun `when setDefaultExpenseCategory with category id should return the success`() = runTest {
        val response = repository.setDefaultExpenseCategory("sampleId")
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getDefaultExpenseCategory after saving the item should return the saved value`() =
        runTest {
            val expectedItem = "sampleId"

            repository.setDefaultExpenseCategory(expectedItem)

            repository.getDefaultExpenseCategory().test {
                val item = awaitItem()
                LWTruth_assertThat(item).isNotNull()
                LWTruth_assertThat(item).isNotEmpty()
                LWTruth_assertThat(item).isEqualTo(expectedItem)
            }
        }

    @Test
    fun `when getDefaultIncomeCategory for first time should return null`() = runTest {
        repository.getDefaultIncomeCategory().test {
            val item = awaitItem()
            LWTruth_assertThat(item).isNull()
        }
    }

    @Test
    fun `when setDefaultIncomeCategory with category id should return the success`() = runTest {
        val response = repository.setDefaultIncomeCategory("sampleId")
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isTrue()
    }

    @Test
    fun `when getDefaultIncomeCategory after saving the item should return the saved value`() =
        runTest {
            val expectedItem = "sampleId"

            repository.setDefaultIncomeCategory(expectedItem)

            repository.getDefaultIncomeCategory().test {
                val item = awaitItem()
                LWTruth_assertThat(item).isNotNull()
                LWTruth_assertThat(item).isNotEmpty()
                LWTruth_assertThat(item).isEqualTo(expectedItem)
            }
        }
}
