package com.naveenapps.expensemanager.core.data4mp.repository

import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data4mp.mappers.toDomainModel
import com.naveenapps.expensemanager.core.data4mp.mappers.toEntityModel
import com.naveenapps.expensemanager.core.database4mp.dao.BudgetDao
import com.naveenapps.expensemanager.core.database4mp.entity.BudgetEntity
import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class BudgetRepositoryImpl(
    private val budgetDao: BudgetDao,
    private val dispatchers: AppCoroutineDispatchers,
) : BudgetRepository {

    override fun getBudgets(): Flow<List<Budget>> {
        return budgetDao.getBudgets().map { budgets ->
            return@map budgets?.map { budget ->
                val accounts = budgetDao.getBudgetAccounts(budget.id)
                val categories = budgetDao.getBudgetCategories(budget.id)
                budget.toDomainModel(
                    accounts = accounts?.map { it.accountId } ?: emptyList(),
                    categories = categories?.map { it.categoryId } ?: emptyList(),
                )
            } ?: emptyList()
        }
    }

    override fun findBudgetByIdFlow(budgetId: String): Flow<Budget?> {
        return budgetDao.findByIdFlow(budgetId).map {
            it?.let {
                convertBudgetModel(it)
            }
        }
    }

    override suspend fun findBudgetById(budgetId: String): Resource<Budget> =
        withContext(dispatchers.io) {
            return@withContext try {
                val budget = budgetDao.findById(budgetId)
                if (budget != null) {
                    Resource.Success(convertBudgetModel(budget))
                } else {
                    Resource.Error(NullPointerException())
                }
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    private suspend fun convertBudgetModel(budget: BudgetEntity): Budget {
        val accounts = budgetDao.getBudgetAccounts(budget.id)
        val categories = budgetDao.getBudgetCategories(budget.id)
        return budget.toDomainModel(
            accounts = accounts?.map { it.accountId } ?: emptyList(),
            categories = categories?.map { it.categoryId } ?: emptyList(),
        )
    }

    override suspend fun addBudget(budget: Budget): Resource<Boolean> =
        withContext(dispatchers.io) {
            return@withContext try {
                val response = budgetDao.insertBudget(
                    budget.toEntityModel(),
                    budget.categories,
                    budget.accounts,
                )
                Resource.Success(response != -1L)
            } catch (exception: Exception) {
                Resource.Error(exception)
            }
        }

    override suspend fun updateBudget(budget: Budget): Resource<Boolean> =
        withContext(dispatchers.io) {
            return@withContext try {
                budgetDao.updateBudget(
                    budget.toEntityModel(),
                    budget.categories,
                    budget.accounts,
                )
                Resource.Success(true)
            } catch (exception: Exception) {
                Resource.Error(exception)
            }
        }

    override suspend fun deleteBudget(budget: Budget): Resource<Boolean> =
        withContext(dispatchers.io) {
            return@withContext try {
                val response = budgetDao.delete(budget.toEntityModel())
                Resource.Success(response > 0)
            } catch (exception: Exception) {
                Resource.Error(exception)
            }
        }
}
