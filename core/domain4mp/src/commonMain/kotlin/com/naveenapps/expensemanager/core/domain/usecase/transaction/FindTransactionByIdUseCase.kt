package com.naveenapps.expensemanager.core.domain.usecase.transaction

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.Transaction

class FindTransactionByIdUseCase(
    private val repository: com.naveenapps.expensemanager.core.repository.TransactionRepository,
) {
    suspend fun invoke(id: String?): Resource<Transaction> {
        if (id.isNullOrEmpty()) {
            return Resource.Error(NullPointerException("Id shouldn't be null"))
        }
        return repository.findTransactionById(id)
    }
}
