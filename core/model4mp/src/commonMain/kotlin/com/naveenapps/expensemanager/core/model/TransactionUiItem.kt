package com.naveenapps.expensemanager.core.model

import com.naveenapps.expensemanager.core.common.utils.toCompleteDateWithDate

data class TransactionGroup(
    val date: String,
    val amountTextColor: Int,
    val totalAmount: Amount,
    val transactions: List<TransactionUiItem>,
    val isLastItem: Boolean = false,
)

data class TransactionUiItem(
    val id: String,
    val amount: Amount = Amount(0.0),
    val notes: String?,
    val categoryName: String,
    val transactionType: TransactionType,
    val categoryIcon: StoredIcon,
    val date: String,
    val fromAccountId: String,
    val fromAccountName: String,
    val fromAccountIcon: StoredIcon,
    val customPos: Int,
    val toAccountName: String? = null,
    val toAccountIcon: StoredIcon? = null,
)

fun Transaction.toTransactionUIModel(amount: Amount, customPos: Int = -1): TransactionUiItem {
    return TransactionUiItem(
        id = this.id,
        customPos = customPos,
        amount = amount,
        notes = this.notes,
        categoryName = this.category.name,
        transactionType = this.type,
        categoryIcon = this.category.storedIcon,
        date = this.createdOn.toCompleteDateWithDate(),
        fromAccountId = this.fromAccountId,
        fromAccountName = this.fromAccount.name,
        fromAccountIcon = this.fromAccount.storedIcon,
        toAccountName = this.toAccount?.name,
        toAccountIcon = this.toAccount?.storedIcon,
    )
}
