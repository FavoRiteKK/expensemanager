package com.naveenapps.expensemanager.core.model4mp

enum class AccountType {
    REGULAR,
    CREDIT,
}

fun AccountType.isRegular() = this == AccountType.REGULAR

fun AccountType.isCredit() = this == AccountType.CREDIT
