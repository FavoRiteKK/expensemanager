package com.naveenapps.expensemanager.core.data4mp.mappers

import com.naveenapps.expensemanager.core.database4mp.entity.AccountEntity
import com.naveenapps.expensemanager.core.model4mp.Account
import com.naveenapps.expensemanager.core.model4mp.StoredIcon

fun Account.toEntityModel(): AccountEntity {
    return AccountEntity(
        id = id,
        name = name,
        type = type,
        iconBackgroundColor = storedIcon.backgroundColor,
        iconName = storedIcon.name,
        amount = amount,
        creditLimit = creditLimit,
        sequence = sequence,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}

fun AccountEntity.toDomainModel(): Account {
    return Account(
        id = id,
        name = name,
        type = type,
        storedIcon = StoredIcon(
            name = iconName,
            backgroundColor = iconBackgroundColor,
        ),
        amount = amount,
        creditLimit = creditLimit,
        sequence = sequence,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}
