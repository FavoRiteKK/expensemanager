package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.category

import com.naveenapps.expensemanager.core.domain4mp.usecase.category.FindCategoryByIdUseCase
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSelectedCategoriesUseCase(
    private val settingsRepository: SettingsRepository,
    private val findCategoryByIdUseCase: FindCategoryByIdUseCase,
) {

    operator fun invoke(): Flow<List<Category>> {
        return settingsRepository.getCategories().map { categoryIds ->
            buildList<Category> {
                if (categoryIds?.isNotEmpty() == true) {
                    categoryIds.forEach { categoryId ->
                        when (val response = findCategoryByIdUseCase.invoke(categoryId)) {
                            is Resource.Error -> Unit
                            is Resource.Success -> {
                                add(response.data)
                            }
                        }
                    }
                }
            }
        }
    }
}