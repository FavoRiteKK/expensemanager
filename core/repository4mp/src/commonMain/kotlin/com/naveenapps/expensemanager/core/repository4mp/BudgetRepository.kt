package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.Resource
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    fun getBudgets(): Flow<List<Budget>>

    fun findBudgetByIdFlow(budgetId: String): Flow<Budget?>

    suspend fun findBudgetById(budgetId: String): Resource<Budget>

    suspend fun addBudget(budget: Budget): Resource<Boolean>

    suspend fun updateBudget(budget: Budget): Resource<Boolean>

    suspend fun deleteBudget(budget: Budget): Resource<Boolean>
}
