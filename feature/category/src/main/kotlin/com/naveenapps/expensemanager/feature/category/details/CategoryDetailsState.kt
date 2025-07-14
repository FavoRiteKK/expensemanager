package com.naveenapps.expensemanager.feature.category4mp.details

import com.naveenapps.expensemanager.core.model.CategoryTransaction
import com.naveenapps.expensemanager.core.model.TransactionUiItem

data class CategoryDetailsState(
    val categoryTransaction: CategoryTransaction?,
    val transactions: List<TransactionUiItem>
)