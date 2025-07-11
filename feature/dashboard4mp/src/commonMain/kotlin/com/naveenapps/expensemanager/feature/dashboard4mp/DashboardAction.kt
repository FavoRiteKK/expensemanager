package com.naveenapps.expensemanager.feature.dashboard4mp

import com.naveenapps.expensemanager.core.domain4mp.usecase.budget.BudgetUiModel
import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.TransactionUiItem

sealed class DashboardAction {

    data object OpenSettings : DashboardAction()

    data class OpenTransactionEdit(val transaction: TransactionUiItem?) : DashboardAction()

    data object OpenTransactionList : DashboardAction()

    data object OpenBudgetList : DashboardAction()

    data class OpenBudgetDetails(val budgetUiModel: BudgetUiModel) : DashboardAction()

    data class OpenAccountEdit(val account: AccountUiModel) : DashboardAction()

    data object OpenAccountList : DashboardAction()
}