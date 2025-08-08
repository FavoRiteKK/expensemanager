package com.naveenapps.expensemanager.core.testing

import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.AccountType
import com.naveenapps.expensemanager.core.model.Amount
import com.naveenapps.expensemanager.core.model.Budget
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.core.model.CategoryType
import com.naveenapps.expensemanager.core.model.StoredIcon
import com.naveenapps.expensemanager.core.model.Transaction
import com.naveenapps.expensemanager.core.model.TransactionType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val FAKE_CATEGORY = Category(
    id = "1",
    name = "Test",
    type = CategoryType.EXPENSE,
    storedIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FFFFFF",
    ),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_SECOND_CATEGORY = Category(
    id = "2",
    name = "Test",
    type = CategoryType.EXPENSE,
    storedIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FFFFFF",
    ),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_FAVORITE_CATEGORY = Category(
    id = "1",
    name = "Test",
    type = CategoryType.EXPENSE,
    storedIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FFFFFF",
    ),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_ACCOUNT = Account(
    id = "1",
    name = "Test",
    type = AccountType.REGULAR,
    storedIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FFFFFF",
    ),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_SECOND_ACCOUNT = Account(
    id = "2",
    name = "Test",
    type = AccountType.REGULAR,
    storedIcon = StoredIcon(
        name = "account_balance",
        backgroundColor = "#FFFFFF",
    ),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_EXPENSE_TRANSACTION = Transaction(
    id = "1",
    notes = "Test",
    categoryId = "1",
    fromAccountId = "1",
    toAccountId = null,
    type = TransactionType.EXPENSE,
    amount = Amount(200.0),
    imagePath = "",
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_INCOME_TRANSACTION = Transaction(
    id = "1",
    notes = "Test",
    categoryId = "1",
    fromAccountId = "1",
    toAccountId = null,
    type = TransactionType.INCOME,
    amount = Amount(200.0),
    imagePath = "",
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_TRANSFER_TRANSACTION = Transaction(
    id = "1",
    notes = "Test",
    categoryId = "1",
    fromAccountId = "1",
    toAccountId = "2",
    type = TransactionType.INCOME,
    amount = Amount(200.0),
    imagePath = "",
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)

@OptIn(ExperimentalTime::class)
val FAKE_BUDGET = Budget(
    id = "1",
    name = "Sample",
    amount = 5000.0,
    selectedMonth = LocalDateTime.Format {   //MMMM yyyy
        monthName(MonthNames.ENGLISH_FULL)
        char(' ')
        year()
    }.format(Clock.System.now().asCurrentDateTime()),
    accounts = emptyList(),
    categories = emptyList(),
    isAllAccountsSelected = true,
    isAllCategoriesSelected = true,
    storedIcon = StoredIcon("ic_account", "#ffffff"),
    createdOn = Clock.System.now().asCurrentDateTime(),
    updatedOn = Clock.System.now().asCurrentDateTime(),
)
