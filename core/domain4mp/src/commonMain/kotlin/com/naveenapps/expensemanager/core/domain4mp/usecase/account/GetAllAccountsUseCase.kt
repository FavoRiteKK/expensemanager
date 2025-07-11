package com.naveenapps.expensemanager.core.domain4mp.usecase.account

import com.naveenapps.expensemanager.core.model4mp.Account
import com.naveenapps.expensemanager.core.repository4mp.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetAllAccountsUseCase(private val repository: AccountRepository) {
    operator fun invoke(): Flow<List<Account>> {
        return repository.getAccounts()
    }
}
