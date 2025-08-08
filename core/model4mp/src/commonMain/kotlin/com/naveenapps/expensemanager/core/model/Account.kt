package com.naveenapps.expensemanager.core.model

import kotlinx.datetime.LocalDateTime

data class Account(
    val id: String,
    val name: String,
    val type: AccountType,
    val storedIcon: StoredIcon,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
    val sequence: Int = Int.MAX_VALUE,
    val amount: Double = 0.0,
    val creditLimit: Double = 0.0,
)

fun Account.getAvailableCreditLimit(): Double {
    return creditLimit + amount
}
