package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.category

import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository

class UpdateSelectedCategoryUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(categories: List<String>?): Resource<Boolean> {
        return settingsRepository.setCategories(categories)
    }
}