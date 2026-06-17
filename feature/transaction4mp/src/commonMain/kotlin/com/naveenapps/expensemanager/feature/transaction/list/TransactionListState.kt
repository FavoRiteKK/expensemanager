package com.naveenapps.expensemanager.feature.transaction.list

data class TransactionListState(
    val transactionListItem: List<TransactionListItem>,
    val selectedPos: Int,
    /** Amount of net worth from first transaction up to selected id */
    val netBalanceString: String,
    val opTransactionId: String,
    val showDeleteDialog: Boolean,
)
