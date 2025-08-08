package com.naveenapps.expensemanager.core.data.repository

import androidx.room.Room
import app.cash.turbine.test
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.lwInMemoryDatabaseBuilder
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import com.naveenapps.expensemanager.core.database.dao.AccountDao
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.AccountRepository
import com.naveenapps.expensemanager.core.testing.BaseCoroutineTest
import com.naveenapps.expensemanager.core.testing.FAKE_ACCOUNT
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class AccountRepositoryImplTest : BaseCoroutineTest() {

    private lateinit var accountDao: AccountDao

    private lateinit var database: ExpenseManagerDatabase

    private lateinit var accountRepository: AccountRepository

    @BeforeTest
    override fun onCreate() {
        super.onCreate()

        database = Room.lwInMemoryDatabaseBuilder().build()

        accountDao = database.accountDao()

        accountRepository = AccountRepositoryImpl(
            accountDao,
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
        assertNotNull(database)
        assertNotNull(accountDao)
    }

    @Test
    fun checkInsertSuccessCase() = runTest {
        addAccountAndAssert(FAKE_ACCOUNT)
    }

    /*@Test
    fun checkInsertWithErrorCase() = runTest {

        whenever(accountDao.insert(any())).thenThrow(IllegalArgumentException(""))

        val result = accountRepository.addAccount(FAKE_ACCOUNT)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Error::class.java)
        val exception = (result as Resource.Error).exception
        LWTruth_assertThat(exception).isNotNull()
    }*/

    @Test
    fun checkDeleteSuccessCase() = runTest {
        addAccountAndAssert(FAKE_ACCOUNT)
        deleteAccountAndAssert(FAKE_ACCOUNT)
    }

    @Test
    fun checkFindByIdSuccessCase() = runTest {
        addAccountAndAssert(FAKE_ACCOUNT)
        findAccountAndAssert(FAKE_ACCOUNT.id)
    }

    @Test
    fun checkGetAllAccountSuccessCase() = runTest {
        addAccountAndAssert(FAKE_ACCOUNT)
        findAccountAndAssert(FAKE_ACCOUNT.id)
    }

    @Test
    fun checkGetAllAccountFlowAfterInsertCase() = runTest {
        accountRepository.getAccounts().test {
            val data = awaitItem()
            assertTrue(data.isEmpty())

            addAccountAndAssert(FAKE_ACCOUNT)

            val secondItem = awaitItem()
            assertTrue(secondItem.isNotEmpty())
            val firstItem = secondItem.first()
            assertNotNull(firstItem)
            assertEquals(FAKE_ACCOUNT.id, firstItem.id)

            deleteAccountAndAssert(FAKE_ACCOUNT)
            val newData = awaitItem()
            assertTrue(newData.isEmpty())
        }
    }

    @Test
    fun checkFindAccountErrorCase() = runTest {
        val newResult = accountRepository.findAccount("Unknown id")
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Error::class)
        val foundData = (newResult as Resource.Error).exception
        LWTruth_assertThat(foundData).isNotNull()
    }

    @Test
    fun checkDeleteAccountErrorCase() = runTest {
        val newResult = accountRepository.deleteAccount(FAKE_ACCOUNT)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isFalse()
    }

    @Test
    fun checkUpdateAllAccountErrorCase() = runTest {
        val newResult = accountRepository.updateAllAccount(listOf(FAKE_ACCOUNT))
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isTrue()
    }

    @Test
    fun checkUpdateCase() = runTest {
        addAccountAndAssert(FAKE_ACCOUNT)
        val name = "New"
        val fakeInsert = FAKE_ACCOUNT.copy(name = name)

        val newResult = accountRepository.updateAccount(fakeInsert)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val data = (newResult as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()

        findAccountAndAssert(FAKE_ACCOUNT.id)
    }

    private suspend fun findAccountAndAssert(accountId: String) {
        val newResult = accountRepository.findAccount(accountId)
        LWTruth_assertThat(newResult).isNotNull()
        LWTruth_assertThat(newResult).isInstanceOf(Resource.Success::class)
        val foundData = (newResult as Resource.Success).data
        LWTruth_assertThat(foundData).isNotNull()
        LWTruth_assertThat(foundData.id).isEqualTo(accountId)
    }

    private suspend fun addAccountAndAssert(account: Account) {
        val result = accountRepository.addAccount(account)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }

    private suspend fun deleteAccountAndAssert(account: Account) {
        val result = accountRepository.deleteAccount(account)
        LWTruth_assertThat(result).isNotNull()
        LWTruth_assertThat(result).isInstanceOf(Resource.Success::class)
        val data = (result as Resource.Success).data
        LWTruth_assertThat(data).isNotNull()
        LWTruth_assertThat(data).isTrue()
    }
}
