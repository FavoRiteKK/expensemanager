package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.theme

import com.naveenapps.expensemanager.core.model4mp.Theme
import com.naveenapps.expensemanager.core.repository4mp.ThemeRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentThemeUseCase(private val repository: ThemeRepository) {

    operator fun invoke(): Flow<Theme> {
        return repository.getSelectedTheme()
    }
}
