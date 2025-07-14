package com.naveenapps.expensemanager.feature.category4mp.list

import com.naveenapps.expensemanager.core.model.Category

sealed class CategoryListAction {

    data object ClosePage : CategoryListAction()

    data object Create : CategoryListAction()

    data class Edit(val item: Category) : CategoryListAction()

    data class ChangeCategory(val type: CategoryTabItems) : CategoryListAction()
}