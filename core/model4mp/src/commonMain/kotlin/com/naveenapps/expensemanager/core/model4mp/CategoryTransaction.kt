package com.naveenapps.expensemanager.core.model4mp

data class CategoryTransaction(
    val category: Category,
    val percent: Float,
    val amount: Amount,
    val transaction: List<Transaction> = emptyList(),
)
