package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.account

import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository

class UpdateSelectedAccountUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(accountId: List<String>?): Resource<Boolean> {
        return settingsRepository.setAccounts(accountId)
    }
}
