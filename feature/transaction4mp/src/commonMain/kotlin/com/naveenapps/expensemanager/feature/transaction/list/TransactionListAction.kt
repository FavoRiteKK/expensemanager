package com.naveenapps.expensemanager.feature.transaction.list

sealed class TransactionListAction {

    data object ClosePage : TransactionListAction()

    data object OpenCreateTransaction : TransactionListAction()

    data class OpenEdiTransaction(val transactionId: String) : TransactionListAction()
    data class BalanceAsLastTransaction(val pos: Int) : TransactionListAction()
    data class ShowDeleteDialog(val transactionId: String) : TransactionListAction()
    data class Delete(val transactionId: String) : TransactionListAction()
    data object DismissDeleteDialog : TransactionListAction()
}
