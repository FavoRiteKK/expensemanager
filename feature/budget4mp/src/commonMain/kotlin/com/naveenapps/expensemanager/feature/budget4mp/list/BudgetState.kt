package com.naveenapps.expensemanager.feature.budget4mp.list

import com.naveenapps.expensemanager.core.domain4mp.usecase.budget.BudgetUiModel

data class BudgetState(
    val isLoading: Boolean,
    val budgets: List<BudgetUiModel>
)
