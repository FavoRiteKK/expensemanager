package com.naveenapps.expensemanager.core.domain4mp.usecase.account

import com.naveenapps.expensemanager.core.model4mp.Account
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.AccountRepository

class DeleteAccountUseCase(
    private val repository: AccountRepository,
    private val checkAccountValidationUseCase: CheckAccountValidationUseCase,
) {

    suspend operator fun invoke(account: Account): Resource<Boolean> {
        return when (val validationResult = checkAccountValidationUseCase(account)) {
            is Resource.Error -> {
                validationResult
            }

            is Resource.Success -> {
                repository.deleteAccount(account)
            }
        }
    }
}
