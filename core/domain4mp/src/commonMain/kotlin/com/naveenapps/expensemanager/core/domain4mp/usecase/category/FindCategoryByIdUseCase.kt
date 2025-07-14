package com.naveenapps.expensemanager.core.domain4mp.usecase.category

import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.CategoryRepository

class FindCategoryByIdUseCase(private val repository: CategoryRepository) {

    suspend operator fun invoke(categoryId: String?): Resource<Category> {
        if (categoryId.isNullOrBlank()) {
            return Resource.Error(Exception("Provide valid category id value"))
        }

        return repository.findCategory(categoryId)
    }
}