package com.naveenapps.expensemanager.core.domain4mp.usecase

import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository
import kotlinx.coroutines.flow.first

class GetOnboardingStatusUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.isOnboardingCompleted().first()
    }
}
