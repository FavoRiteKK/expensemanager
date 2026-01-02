package com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account

import com.naveenapps.expensemanager.core.domain.usecase.account.FindAccountByIdUseCase
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class GetSelectedAccountUseCase(
    private val settingsRepository: SettingsRepository,
    private val findAccountByIdUseCase: FindAccountByIdUseCase,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(accId: String = ""): Flow<List<Account>?> {
        val accIds = if ("" != accId) {
            settingsRepository.getFilterByAccount(accId).mapLatest { listOf(it) }
        } else {
            settingsRepository.getSelectedAccounts()
        }
        return accIds.map { accountIds ->
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
