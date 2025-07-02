package com.naveenapps.expensemanager.core.domain4mp.usecase.budget

import com.naveenapps.expensemanager.core.common4mp.utils.fromMonthAndYear
import com.naveenapps.expensemanager.core.common4mp.utils.getEndOfTheMonth
import com.naveenapps.expensemanager.core.common4mp.utils.getStartOfTheMonth
import com.naveenapps.expensemanager.core.common4mp.utils.toMonthAndYear
import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.CategoryType
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.model4mp.Transaction
import com.naveenapps.expensemanager.core.repository4mp.AccountRepository
import com.naveenapps.expensemanager.core.repository4mp.CategoryRepository
import com.naveenapps.expensemanager.core.repository4mp.TransactionRepository
import kotlinx.coroutines.flow.firstOrNull

class GetBudgetTransactionsUseCase(
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {
    suspend operator fun invoke(budget: Budget): Resource<List<Transaction>> {
        val accounts: List<String> = if (budget.isAllAccountsSelected) {
            accountRepository.getAccounts().firstOrNull()?.map { it.id } ?: emptyList()
        } else {
            budget.accounts
        }

        val categories: List<String> = if (budget.isAllCategoriesSelected) {
            categoryRepository.getCategories().firstOrNull()?.map { it.id } ?: emptyList()
        } else {
            budget.categories
        }

        val date = budget.selectedMonth.fromMonthAndYear()

        date ?: return Resource.Error(IllegalArgumentException("Unknown month value"))

        val startDayOfMonth = date.getStartOfTheMonth()
        val endDayOfMonth = date.getEndOfTheMonth()

        val transaction = transactionRepository.getFilteredTransaction(
            accounts = accounts,
            categories = categories,
            transactionType = CategoryType.entries.map { it.ordinal }.toList(),
            startDate = startDayOfMonth,
            endDate = endDayOfMonth,
        ).firstOrNull()?.filter {
            it.createdOn.toMonthAndYear() == budget.selectedMonth
        }

        return Resource.Success(transaction ?: emptyList())
    }
}
