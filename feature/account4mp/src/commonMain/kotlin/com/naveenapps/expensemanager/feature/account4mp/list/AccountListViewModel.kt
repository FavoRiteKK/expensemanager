package com.naveenapps.expensemanager.feature.account4mp.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.domain4mp.usecase.account.GetAllAccountsUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.model4mp.AccountType
import com.naveenapps.expensemanager.core.model4mp.AccountUiModel
import com.naveenapps.expensemanager.core.model4mp.getAvailableCreditLimit
import com.naveenapps.expensemanager.core.model4mp.toAccountUiModel
import com.naveenapps.expensemanager.core.navigation4mp.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation4mp.ExpenseManagerScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

class AccountListViewModel(
    getAllAccountsUseCase: GetAllAccountsUseCase,
    getCurrencyUseCase: GetCurrencyUseCase,
    private val getFormattedAmountUseCase: GetFormattedAmountUseCase,
    private val appComposeNavigator: AppComposeNavigator,
) : ViewModel() {

    private val _state = MutableStateFlow(
        AccountListState(
            accounts = emptyList(),
            showReOrder = false
        )
    )
    val state = _state.asStateFlow()

    init {
        combine(
            getCurrencyUseCase.invoke(),
            getAllAccountsUseCase.invoke(),
        ) { currency, accounts ->
            val list = accounts.map {
                it.toAccountUiModel(
                    getFormattedAmountUseCase.invoke(
                        it.amount,
                        currency,
                    ),
                    if (it.type == AccountType.CREDIT) {
                        getFormattedAmountUseCase.invoke(
                            it.getAvailableCreditLimit(),
                            currency
                        )
                    } else {
                        null
                    }
                )
            }

            _state.update {
                it.copy(
                    accounts = list,
                    showReOrder = accounts.isNotEmpty() && accounts.size > 1
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun openCreateScreen(account: AccountUiModel?) {
        appComposeNavigator.navigate(
            ExpenseManagerScreens.AccountCreate(account?.id),
        )
    }

    private fun closePage() {
        appComposeNavigator.popBackStack()
    }

    private fun openAccountReOrderScreen() {
        appComposeNavigator.navigate(ExpenseManagerScreens.AccountReOrderScreen)
    }

    fun processAction(action: AccountListAction) {
        when (action) {
            AccountListAction.ClosePage -> closePage()
            AccountListAction.CreateAccount -> openCreateScreen(null)
            is AccountListAction.EditAccount -> openCreateScreen(action.accountUiModel)
            AccountListAction.OpenReOrder -> openAccountReOrderScreen()
        }
    }
}
