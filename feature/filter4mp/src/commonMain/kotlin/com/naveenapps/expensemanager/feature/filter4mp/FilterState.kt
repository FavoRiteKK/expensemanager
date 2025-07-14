package com.naveenapps.expensemanager.feature.filter4mp

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.DateRangeType
import com.naveenapps.expensemanager.core.model4mp.TransactionType

data class FilterState(
    val date: String,
    val dateRangeType: DateRangeType,
    val selectedCategories: List<Category>,
    val selectedAccounts: List<AccountUiModel>,
    val selectedTransactionTypes: List<TransactionType>,
    val showForward: Boolean,
    val showBackward: Boolean,
    val showDateFilter: Boolean,
    val showTypeFilter: Boolean,
)