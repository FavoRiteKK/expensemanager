package com.naveenapps.expensemanager.core.domain4mp.usecase.account

import com.naveenapps.expensemanager.core.model4mp.Account
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.AccountRepository

class FindAccountByIdUseCase(private val repository: AccountRepository) {

    suspend operator fun invoke(accountId: String?): Resource<Account> {
        if (accountId.isNullOrBlank()) {
            return Resource.Error(Exception("Provide valid account id value"))
        }

        return repository.findAccount(accountId)
    }
}
