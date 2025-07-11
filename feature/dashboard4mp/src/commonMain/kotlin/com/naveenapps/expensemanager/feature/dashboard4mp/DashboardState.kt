package com.naveenapps.expensemanager.feature.dashboard4mp

import com.naveenapps.expensemanager.core.domain4mp.usecase.budget.BudgetUiModel
import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.CategoryTransactionState
import com.naveenapps.expensemanager.core.model4mp.ExpenseFlowState
import com.naveenapps.expensemanager.core.model4mp.TransactionUiItem

data class DashboardState(
    val expenseFlowState: ExpenseFlowState,
    val transactions: List<TransactionUiItem>,
    val budgets: List<BudgetUiModel>,
    val accounts: List<AccountUiModel>,
    val categoryTransactionState: CategoryTransactionState
)