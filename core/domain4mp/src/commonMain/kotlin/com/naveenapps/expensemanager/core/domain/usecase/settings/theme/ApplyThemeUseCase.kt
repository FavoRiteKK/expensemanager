package com.naveenapps.expensemanager.core.domain.usecase.settings.theme


class ApplyThemeUseCase (private val repository: com.naveenapps.expensemanager.core.repository.ThemeRepository) {

    suspend operator fun invoke() {
        repository.applyTheme()
    }
}
