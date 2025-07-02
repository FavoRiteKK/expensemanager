package com.naveenapps.expensemanager.core.domain4mp.usecase.budget

import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.BudgetRepository

class FindBudgetByIdUseCase(private val repository: BudgetRepository) {

    suspend operator fun invoke(budgetId: String?): Resource<Budget> {
        if (budgetId.isNullOrBlank()) {
            return Resource.Error(Exception("Provide valid budget id value"))
        }

        return repository.findBudgetById(budgetId)
    }
}
