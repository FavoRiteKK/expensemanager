package com.naveenapps.expensemanager.core.domain4mp.usecase.category

import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.CategoryRepository

class UpdateCategoryUseCase(
    private val repository: CategoryRepository,
    private val checkCategoryValidationUseCase: CheckCategoryValidationUseCase,
) {

    suspend operator fun invoke(category: Category): Resource<Boolean> {
        return when (val validationResult = checkCategoryValidationUseCase(category)) {
            is Resource.Error -> {
                validationResult
            }

            is Resource.Success -> {
                repository.updateCategory(category)
            }
        }
    }
}