package com.naveenapps.expensemanager.core.domain4mp.usecase.budget

import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.BudgetRepository

class UpdateBudgetUseCase(
    private val repository: BudgetRepository,
    private val checkBudgetValidateUseCase: CheckBudgetValidateUseCase,
) {

    suspend operator fun invoke(budget: Budget): Resource<Boolean> {
        return when (val validationResult = checkBudgetValidateUseCase(budget)) {
            is Resource.Error -> {
                validationResult
            }

            is Resource.Success -> {
                repository.updateBudget(budget)
            }
        }
    }
}
