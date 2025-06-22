package com.naveenapps.expensemanager.core.model4mp

enum class CategoryType {
    INCOME,
    EXPENSE,
}

fun CategoryType.isIncome(): Boolean {
    return this == CategoryType.INCOME
}

fun CategoryType.isExpense(): Boolean {
    return this == CategoryType.EXPENSE
}
