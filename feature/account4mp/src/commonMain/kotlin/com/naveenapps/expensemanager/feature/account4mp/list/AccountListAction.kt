package com.naveenapps.expensemanager.feature.account4mp.list

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel

sealed class AccountListAction {

    data object ClosePage : AccountListAction()

    data object OpenReOrder : AccountListAction()

    data object CreateAccount : AccountListAction()

    data class EditAccount(val accountUiModel: AccountUiModel) : AccountListAction()
}