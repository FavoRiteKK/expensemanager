package com.naveenapps.expensemanager.feature.category4mp.create

import com.naveenapps.expensemanager.core.model4mp.CategoryType
import com.naveenapps.expensemanager.core.model4mp.TextFieldValue

data class CategoryCreateState(
    val name: TextFieldValue<String>,
    val color: TextFieldValue<String>,
    val icon: TextFieldValue<String>,
    val type: TextFieldValue<CategoryType>,
    val showDeleteButton: Boolean,
    val showDeleteDialog: Boolean,
)
