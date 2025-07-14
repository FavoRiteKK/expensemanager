package com.naveenapps.expensemanager.feature.filter4mp

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.TransactionType

sealed class FilterAction {

    data object MoveDateForward : FilterAction()

    data object MoveDateBackward : FilterAction()

    data object ShowDateFilter : FilterAction()

    data object DismissDateFilter : FilterAction()

    data object ShowTypeFilter : FilterAction()

    data object DismissTypeFilter : FilterAction()

    data class RemoveAccount(val account: AccountUiModel) : FilterAction()

    data class RemoveCategory(val category: Category) : FilterAction()

    data class RemoveTransactionType(val transactionType: TransactionType) : FilterAction()
}