package com.naveenapps.expensemanager.core.data.repository

import androidx.room.Room
import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.lwInMemoryDatabaseBuilder
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import com.naveenapps.expensemanager.core.database.dao.AccountDao
import com.naveenapps.expensemanager.core.database.dao.BudgetDao
import com.naveenapps.expensemanager.core.database.dao.CategoryDao
import com.naveenapps.expensemanager.core.model.Budget
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BudgetRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_BUDGET
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class BudgetRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var categoryDao: CategoryDao
    private lateinit var accountDao: AccountDao
    private lateinit var budgetDao: BudgetDao

    private lateinit var database: ExpenseManagerDatabase

    private lateinit var budgetRepository: BudgetRepository

    @BeforeTest
    override fun onCreate() {
        super.onCreate()

        database = Room.lwInMemoryDatabaseBuilder().build()

        categoryDao = database.categoryDao()
        accountDao = database.accountDao()
        budgetDao = database.budgetDao()

        budgetRepository = BudgetRepositoryImpl(
            budgetDao,
            AppCoroutineDispatchers(
                testCoroutineDispatcher,
                testCoroutineDispatcher,
                testCoroutineDispatcher,
            ),
        )
    }

    @AfterTest
    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    @Test
    fun checkDatabaseObject() {
        LWTruth_assertThat(database).isNotNull()
        LWTruth_assertThat(categoryDao).isNotNull()
        LWTruth_assertThat(accountDao).isNotNull()
        LWTruth_assertThat(budgetDao).isNotNull()
    }

    @Test
    fun checkInsertSuccessCase() = runTest {
        addBudgetAndAssert(FAKE_BUDGET)
    }

    @Test
    fun checkDeleteSuccessCase() = runTest {
        addBudgetAndAssert(FAKE_BUDGET)
        deleteBudgetAndAssert(FAKE_BUDGET)
    }

    @Test
    fun checkFindByIdSuccessCase() = runTest {
        addBudgetAndAssert(FAKE_BUDGET)
        findBudgetAndAssert(FAKE_BUDGET.id)
    }

    @Test
    fun checkGetAllBudgetSuccessCase() = runTest {
        addBudgetAndAssert(FAKE_BUDGET)
        findBudgetAndAssert(FAKE_BUDGET.id)
    }

    @Test
    fun checkGetAllBudgetFlowAfterInsertCase() = runTest {
        budgetRepository.getBudgets().test {
            val data = awaitItem()
            LWTruth_assertThat(data).isEmpty()

            addBudgetAndAssert(FAKE_BUDGET)

            val secondItem = awaitItem()
            LWTruth_assertThat(secondItem).isNotEmpty()
            val firstItem = secondItem.first()
            LWTruth_assertThat(firstItem).isNotNull()
            LWTruth_assertThat(firstItem.id).isEqualTo(FAKE_BUDGET.id)

            deleteBudgetAndAssert(FAKE_BUDGET)
            val newData = awaitItem()
            LWTruth_assertThat(newData).isEmpty()
        }
    }

    @Test
    fun checkFindBudgetErrorCase() = runTest {
        val newResult = budgetRepository.findBudgetById("Unknown id")
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Error::class)
        val foundData = (newResult as Resource.Error).exception
        LWTruth_assertThat(foundData).isNotNull()
    }

    @Test
    fun checkDeleteBudgetErrorCase() = runTest {
        val newResult = budgetRepository.deleteBudget(FAKE_BUDGET)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isFalse()
    }

    @Test
    fun checkUpdateCase() = runTest {
        addBudgetAndAssert(FAKE_BUDGET)
        val name = "New"
        val fakeInsert = FAKE_BUDGET.copy(name = name)

        val newResult = budgetRepository.updateBudget(fakeInsert)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val data = (newResult as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()

        findBudgetAndAssert(FAKE_BUDGET.id)
    }

    @Test
    fun whenFindTheBudgetAsFlow() = runTest {
        budgetRepository.findBudgetByIdFlow(FAKE_BUDGET.id).test {
            val response = awaitItem()
            LWTruth_assertThat(response).isNull()

            addBudgetAndAssert(FAKE_BUDGET)

            val updatedItem = awaitItem()
            LWTruth_assertThat(updatedItem).isNotNull()
            LWTruth_assertThat(updatedItem?.id).isEqualTo(FAKE_BUDGET.id)

            deleteBudgetAndAssert(FAKE_BUDGET)

            val afterUpdateItem = awaitItem()
            LWTruth_assertThat(afterUpdateItem).isNull()
        }
    }

    private suspend fun findBudgetAndAssert(accountId: String) {
        val newResult = budgetRepository.findBudgetById(accountId)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isNotNull()
        LWTruth_assertThat(foundData.id).isEqualTo(accountId)
    }

    private suspend fun addBudgetAndAssert(account: Budget) {
        val result = budgetRepository.addBudget(account)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun deleteBudgetAndAssert(account: Budget) {
        val result = budgetRepository.deleteBudget(account)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }
}
