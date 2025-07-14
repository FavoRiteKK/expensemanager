package com.naveenapps.expensemanager.feature.filter4mp.type

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.TransactionType

sealed class FilterTypeAction {

    data class SelectAccount(val account: AccountUiModel) : FilterTypeAction()

    data class SelectCategory(val category: Category) : FilterTypeAction()

    data class SelectTransactionType(val transactionType: TransactionType) : FilterTypeAction()

    data object SaveChanges : FilterTypeAction()
}