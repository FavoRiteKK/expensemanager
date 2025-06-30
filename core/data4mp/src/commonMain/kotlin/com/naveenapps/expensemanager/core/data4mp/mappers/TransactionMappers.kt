package com.naveenapps.expensemanager.core.data4mp.mappers

import com.naveenapps.expensemanager.core.database4mp.entity.TransactionEntity
import com.naveenapps.expensemanager.core.model4mp.Amount
import com.naveenapps.expensemanager.core.model4mp.Transaction

fun Transaction.toEntityModel(): TransactionEntity {
    return TransactionEntity(
        id = id,
        notes = notes,
        categoryId = categoryId,
        fromAccountId = fromAccountId,
        toAccountId = toAccountId,
        type = type,
        amount = amount.amount,
        imagePath = imagePath,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}

fun TransactionEntity.toDomainModel(): Transaction {
    return Transaction(
        id = id,
        notes = notes,
        categoryId = categoryId,
        fromAccountId = fromAccountId,
        toAccountId = toAccountId,
        type = type,
        amount = Amount(amount),
        imagePath = imagePath,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}
