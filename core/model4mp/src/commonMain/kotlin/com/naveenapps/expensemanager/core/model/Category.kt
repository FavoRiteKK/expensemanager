package com.naveenapps.expensemanager.core.model

import kotlinx.datetime.LocalDateTime

data class Category(
    val id: String,
    val name: String,
    val type: CategoryType,
    val storedIcon: StoredIcon,
    val createdOn: LocalDateTime,
    val updatedOn: LocalDateTime,
)
