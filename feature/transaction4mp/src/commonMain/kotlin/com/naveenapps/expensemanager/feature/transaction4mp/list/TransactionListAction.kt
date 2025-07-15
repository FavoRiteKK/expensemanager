package com.naveenapps.expensemanager.feature.transaction4mp.list

sealed class TransactionListAction {

    data object ClosePage : TransactionListAction()

    data object OpenCreateTransaction : TransactionListAction()

    data class OpenEdiTransaction(val transactionId: String) : TransactionListAction()
}