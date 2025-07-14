package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.filter.account

import com.naveenapps.expensemanager.core.domain4mp.usecase.account.FindAccountByIdUseCase
import com.naveenapps.expensemanager.core.model4mp.Account
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSelectedAccountUseCase(
    private val settingsRepository: SettingsRepository,
    private val findAccountByIdUseCase: FindAccountByIdUseCase,
) {

    operator fun invoke(): Flow<List<Account>?> {
        return settingsRepository.getAccounts().map { accountIds ->
            return@map buildList<Account> {
                if (accountIds?.isNotEmpty() == true) {
                    repeat(accountIds.size) {
                        val accountId = accountIds[it]
                        when (val response = findAccountByIdUseCase.invoke(accountId)) {
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
