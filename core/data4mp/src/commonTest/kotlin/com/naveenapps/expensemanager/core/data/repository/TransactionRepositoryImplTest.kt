package com.naveenapps.expensemanager.core.data.repository

import androidx.room.Room
import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.lwInMemoryDatabaseBuilder
import com.naveenapps.expensemanager.core.data.mappers.toEntityModel
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import com.naveenapps.expensemanager.core.database.dao.AccountDao
import com.naveenapps.expensemanager.core.database.dao.CategoryDao
import com.naveenapps.expensemanager.core.database.dao.TransactionDao
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.Transaction
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.repository.TransactionRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_ACCOUNT
import com.naveenapps.expensemanager.core.testing.FAKE_CATEGORY
import com.naveenapps.expensemanager.core.testing.FAKE_EXPENSE_TRANSACTION
import com.naveenapps.expensemanager.core.testing.FAKE_INCOME_TRANSACTION
import com.naveenapps.expensemanager.core.testing.FAKE_SECOND_ACCOUNT
import com.naveenapps.expensemanager.core.testing.FAKE_SECOND_CATEGORY
import com.naveenapps.expensemanager.core.testing.FAKE_TRANSFER_TRANSACTION
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@ExperimentalCoroutinesApi
class TransactionRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var accountDao: AccountDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var transactionDao: TransactionDao

    private lateinit var database: ExpenseManagerDatabase

    private lateinit var transactionRepository: TransactionRepository

    @BeforeTest
    override fun onCreate() {
        super.onCreate()

        database = Room.lwInMemoryDatabaseBuilder().build()

        transactionDao = database.transactionDao()
        accountDao = database.accountDao()
        categoryDao = database.categoryDao()

        transactionRepository = TransactionRepositoryImpl(
            transactionDao,
            accountDao,
            categoryDao,
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

    /**
     * This below test will throw an error. There is no account and category available in the
     * database.
     */
    @Test
    fun transactionInsertFailureCase() = runTest {
        val result = transactionRepository.addTransaction(FAKE_EXPENSE_TRANSACTION)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Error::class)
        val exception = (result as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
    }

    @Test
    fun transactionInsertSuccessCase() = runTest {
        setupBasicAppData()
        insertTransactionAndAssert(FAKE_EXPENSE_TRANSACTION)
    }

    @Test
    fun expenseTransactionInsertSuccessAndCheckTransactionValues() = runTest {
        setupBasicAppData()
        insertTransactionAndAssert(FAKE_EXPENSE_TRANSACTION)
        validateTransaction(FAKE_EXPENSE_TRANSACTION)
    }

    @Test
    fun incomeTransactionInsertSuccessAndCheckTransactionValues() = runTest {
        setupBasicAppData()
        insertTransactionAndAssert(FAKE_INCOME_TRANSACTION)
        validateTransaction(FAKE_INCOME_TRANSACTION)
    }

    @Test
    fun transferTransactionInsertSuccessAndCheckTransactionValues() = runTest {
        setupBasicAppData()
        insertTransactionAndAssert(FAKE_TRANSFER_TRANSACTION)
        validateTransaction(FAKE_TRANSFER_TRANSACTION)
    }

    @Test
    fun deleteTransactionSuccessAndCheck() = runTest {
        setupBasicAppData()
        insertTransactionAndAssert(FAKE_INCOME_TRANSACTION)
        deleteTransactionAndAssert(FAKE_INCOME_TRANSACTION)
    }

    @Test
    fun getAllTransactions() = runTest {
        setupBasicAppData()

        insertTransactionAndAssert(FAKE_INCOME_TRANSACTION)

        transactionRepository.getAllTransaction().test {
            val firstItem = awaitItem()
            LWTruth_assertThat(firstItem).isNotNull()
            LWTruth_assertThat(firstItem).isNotEmpty()
            LWTruth_assertThat(firstItem).hasSize(1)
            val transaction = firstItem?.firstOrNull()
            LWTruth_assertThat(transaction).isNotNull()
            LWTruth_assertThat(transaction!!.id).isEqualTo(FAKE_INCOME_TRANSACTION.id)
        }
    }

    @Test
    fun updateTransaction() = runTest {
        setupBasicAppData()

        insertTransactionAndAssert(FAKE_INCOME_TRANSACTION)
        val updatedTransaction = FAKE_INCOME_TRANSACTION.copy(notes = "Sample Notes")
        updateTransactionAndAssert(updatedTransaction)

        validateTransaction(updatedTransaction)
    }

    @Test
    fun findTransactionForUnknownId() = runTest {
        setupBasicAppData()
        val result = transactionRepository.findTransactionById("unknownId")
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Error::class)
        LWTruth_assertThat((result as Resource.Error).exception).isNotNull()
    }

    private suspend fun validateTransaction(addedTransaction: Transaction) {
        val response = transactionRepository.findTransactionById(addedTransaction.id)
        LWTruth_assertThat(response).isNotNull()
        LWTruth_assertThat(response).isInstanceOf(Resource.Success::class)
        val transaction = (response as Resource.Success).data
        LWTruth_assertThat(transaction).isNotNull()
        LWTruth_assertThat(transaction.type).isEqualTo(addedTransaction.type)
        LWTruth_assertThat(transaction.amount).isEqualTo(addedTransaction.amount)
        LWTruth_assertThat(transaction.fromAccountId).isEqualTo(addedTransaction.fromAccountId)
        LWTruth_assertThat(transaction.notes).isEqualTo(addedTransaction.notes)
        LWTruth_assertThat(transaction.fromAccount).isNotNull()
        LWTruth_assertThat(transaction.fromAccount.amount).isEqualTo(
            when (addedTransaction.type) {
                TransactionType.INCOME -> (addedTransaction.amount.amount)
                TransactionType.EXPENSE -> (addedTransaction.amount.amount * -1)
                else -> (addedTransaction.amount.amount)
            },
        )
        if (addedTransaction.type == TransactionType.TRANSFER) {
            LWTruth_assertThat(transaction.toAccountId).isEqualTo(addedTransaction.toAccountId)
            LWTruth_assertThat(transaction.toAccount).isNotNull()
        }
    }

    private suspend fun insertTransactionAndAssert(transaction: Transaction) {
        val result = transactionRepository.addTransaction(transaction)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun updateTransactionAndAssert(transaction: Transaction) {
        val result = transactionRepository.updateTransaction(transaction)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun deleteTransactionAndAssert(transaction: Transaction) {
        val result = transactionRepository.deleteTransaction(transaction)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun setupBasicAppData() {
        accountDao.insert(FAKE_ACCOUNT.toEntityModel())
        accountDao.insert(FAKE_SECOND_ACCOUNT.toEntityModel())
        categoryDao.insert(FAKE_CATEGORY.toEntityModel())
        categoryDao.insert(FAKE_SECOND_CATEGORY.toEntityModel())
    }
}
