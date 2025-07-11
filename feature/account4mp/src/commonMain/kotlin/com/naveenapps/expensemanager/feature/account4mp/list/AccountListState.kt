package com.naveenapps.expensemanager.feature.account4mp.list

import com.naveenapps.expensemanager.core.model4mp.AccountUiModel

data class AccountListState(
    val showReOrder: Boolean,
    val accounts: List<AccountUiModel>
)