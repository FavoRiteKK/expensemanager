package com.naveenapps.expensemanager.core.domain4mp.usecase.transaction

import com.naveenapps.expensemanager.core.model4mp.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetIncomeAmountUseCase(
    private val getTransactionWithFilterUseCase: GetTransactionWithFilterUseCase,
) {

    operator fun invoke(): Flow<Double?> {
        return getTransactionWithFilterUseCase.invoke().map { transactions ->
            return@map transactions?.filter { it.type == TransactionType.INCOME }?.sumOf {
                it.amount.amount
            } ?: 0.0
        }
    }
}
