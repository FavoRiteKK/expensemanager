package com.naveenapps.expensemanager.core.domain.usecase.budget

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.AccountRepository
import com.naveenapps.expensemanager.core.repository.CategoryRepository
import com.naveenapps.expensemanager.core.repository.TransactionRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_ACCOUNT
import com.naveenapps.expensemanager.core.testing.FAKE_BUDGET
import com.naveenapps.expensemanager.core.testing.FAKE_CATEGORY
import com.naveenapps.expensemanager.core.testing.FAKE_EXPENSE_TRANSACTION
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.kodein.mock.Mocker
import org.kodein.mock.UsesMocks
import org.kodein.mock.generated.mock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
@UsesMocks(CategoryRepository::class, AccountRepository::class, TransactionRepository::class)
class GetBudgetTransactionsUseCaseTest : BaseCoroutineTest() {

    private val mocker = Mocker()
    private val categoryRepository: CategoryRepository by lazyOf(mocker.mock())
    private val accountRepository: AccountRepository by lazyOf(mocker.mock())
    private val transactionRepository: TransactionRepository by lazyOf(mocker.mock())

    private lateinit var getBudgetTransactionsUseCase: GetBudgetTransactionsUseCase

    @BeforeTest
    override fun onCreate() {
        super.onCreate()
        getBudgetTransactionsUseCase = GetBudgetTransactionsUseCase(
            categoryRepository,
            accountRepository,
            transactionRepository,
        )
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
    }

    @Test
    fun whenAllDataAvailableShouldReturnListOfTransactionAssociatedWithBudget() = runTest {
        mocker.every { categoryRepository.getCategories() } returns flowOf(listOf(FAKE_CATEGORY))
        mocker.every { accountRepository.getAccounts() } returns flowOf(listOf(FAKE_ACCOUNT))
        mocker.every {
            transactionRepository.getFilteredTransaction(
                isAny(), isAny(), isAny(), isAny(), isAny()
            )
        } returns flowOf(listOf(FAKE_EXPENSE_TRANSACTION))

        val response = getBudgetTransactionsUseCase.invoke(FAKE_BUDGET)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isNotEmpty()
        LWTruth_assertThat(response.data).hasSize(1)
    }

    @Test
    fun whenThereIsNoDataItShouldBeEmpty() = runTest {
        mocker.every { categoryRepository.getCategories() } returns flowOf(listOf(FAKE_CATEGORY))
        mocker.every { accountRepository.getAccounts() } returns flowOf(listOf(FAKE_ACCOUNT))
        mocker.every {
            transactionRepository.getFilteredTransaction(
                isAny(), isAny(), isAny(), isAny(), isAny()
            )
        } returns flowOf(listOf())

        val response = getBudgetTransactionsUseCase.invoke(FAKE_BUDGET)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        LWTruth_assertThat((response as Resource.Success).data).isEmpty()
    }
}
