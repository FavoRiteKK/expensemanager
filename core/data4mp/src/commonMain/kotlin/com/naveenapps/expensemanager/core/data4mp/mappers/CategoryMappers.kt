package com.naveenapps.expensemanager.core.data4mp.mappers

import com.naveenapps.expensemanager.core.database4mp.entity.CategoryEntity
import com.naveenapps.expensemanager.core.model4mp.Category
import com.naveenapps.expensemanager.core.model4mp.StoredIcon

fun Category.toEntityModel(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        type = type,
        iconName = storedIcon.name,
        iconBackgroundColor = storedIcon.backgroundColor,
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}

fun CategoryEntity.toDomainModel(): Category {
    return Category(
        id = id,
        name = name,
        type = type,
        storedIcon = StoredIcon(
            name = iconName,
            backgroundColor = iconBackgroundColor,
        ),
        createdOn = createdOn,
        updatedOn = updatedOn,
    )
}
