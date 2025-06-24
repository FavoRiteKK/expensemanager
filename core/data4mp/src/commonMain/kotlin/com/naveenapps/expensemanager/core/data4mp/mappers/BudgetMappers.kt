package com.naveenapps.expensemanager.core.data4mp.mappers

import com.naveenapps.expensemanager.core.database4mp.entity.BudgetEntity
import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.StoredIcon

fun Budget.toEntityModel(): BudgetEntity {
    return BudgetEntity(
        id = id,
        name = name,
        iconBackgroundColor = storedIcon.backgroundColor,
        iconName = storedIcon.name,
        amount = amount,
        selectedMonth = selectedMonth,
        isAllCategoriesSelected = isAllCategoriesSelected,
        isAllAccountsSelected = isAllAccountsSelected,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}

fun BudgetEntity.toDomainModel(categories: List<String>, accounts: List<String>): Budget {
    return Budget(
        id = id,
        name = name,
        storedIcon = StoredIcon(
            name = iconName,
            backgroundColor = iconBackgroundColor,
        ),
        amount = amount,
        selectedMonth = selectedMonth,
        categories = categories,
        accounts = accounts,
        isAllCategoriesSelected = isAllCategoriesSelected,
        isAllAccountsSelected = isAllAccountsSelected,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}
