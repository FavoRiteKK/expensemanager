package com.naveenapps.expensemanager.feature.filter4mp.type

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.TransactionType

data class FilterTypeState(
    val categories: List<Category>,
    val selectedCategories: List<Category>,
    val accounts: List<AccountUiModel>,
    val selectedAccounts: List<AccountUiModel>,
    val transactionTypes: List<TransactionType>,
    val selectedTransactionTypes: List<TransactionType>,
)