package com.naveenapps.expensemanager.core.repository

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getTransactionTypes(): Flow<List<TransactionType>?>

    suspend fun setTransactionTypes(transactionTypes: List<TransactionType>?): Resource<Boolean>

    fun getSelectedAccounts(): Flow<List<String>?>

    fun getFilterByAccount(accId: String): Flow<String>

    suspend fun setSelectedAccounts(accounts: List<String>?): Resource<Boolean>

    suspend fun setFilterByAccount(accId: String): Resource<Boolean>

    fun getCategories(): Flow<List<String>?>

    suspend fun setCategories(categories: List<String>?): Resource<Boolean>

    fun isPreloaded(): Flow<Boolean>

    suspend fun setPreloaded(preloaded: Boolean): Resource<Boolean>

    fun isOnboardingCompleted(): Flow<Boolean>

    suspend fun setOnboardingCompleted(isOnboardingCompleted: Boolean): Resource<Boolean>

    fun getDefaultAccount(): Flow<String?>

    suspend fun setDefaultAccount(accountId: String): Resource<Boolean>

    fun getDefaultExpenseCategory(): Flow<String?>

    suspend fun setDefaultExpenseCategory(categoryId: String): Resource<Boolean>

    fun getDefaultIncomeCategory(): Flow<String?>

    suspend fun setDefaultIncomeCategory(categoryId: String): Resource<Boolean>
}
