package com.naveenapps.expensemanager.core.model

import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import kotlinx.datetime.LocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class Transaction @OptIn(ExperimentalTime::class) constructor(
    val id: String,
    val notes: String,
    val categoryId: String,
    val fromAccountId: String,
    val toAccountId: String?,
    val amount: Amount,
    val imagePath: String,
    val type: TransactionType,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
    var category: Category = Category(
        "",
        "",
        CategoryType.INCOME,
        StoredIcon("", ""),
        Clock.System.now().asCurrentDateTime(),
        Clock.System.now().asCurrentDateTime(),
    ),
    var fromAccount: Account = Account(
        "",
        "",
        AccountType.REGULAR,
        StoredIcon("", ""),
        Clock.System.now().asCurrentDateTime(),
        Clock.System.now().asCurrentDateTime(),
    ),
    var toAccount: Account? = null,
)
