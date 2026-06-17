package com.naveenapps.expensemanager.feature.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.domain.usecase.account.GetAllAccountsUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account.GetSelectedAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account.UpdateSelectedAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.category.GetSelectedCategoriesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.category.UpdateSelectedCategoryUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.MoveDateRangeBackwardUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.MoveDateRangeForwardUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.transactiontype.GetSelectedTransactionTypesUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.transactiontype.UpdateSelectedTransactionTypesUseCase
import com.naveenapps.expensemanager.core.model.AccountUiModel
import com.naveenapps.expensemanager.core.model.Amount
import com.naveenapps.expensemanager.core.model.Category
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.model.includeToday
import com.naveenapps.expensemanager.core.model.toAccountUiModel
import com.naveenapps.expensemanager.feature.filter.type.addOrRemove
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilterViewModel(
    getSelectedTransactionTypesUseCase: GetSelectedTransactionTypesUseCase,
    getSelectedAccountUseCase: GetSelectedAccountUseCase,
    getSelectedCategoriesUseCase: GetSelectedCategoriesUseCase,
    getDateRangeUseCase: GetDateRangeUseCase,
    getAllAccountsUseCase: GetAllAccountsUseCase,
    private val moveDateRangeBackwardUseCase: MoveDateRangeBackwardUseCase,
    private val moveDateRangeForwardUseCase: MoveDateRangeForwardUseCase,
    private val updateSelectedTransactionTypesUseCase: UpdateSelectedTransactionTypesUseCase,
    private val updateSelectedCategoryUseCase: UpdateSelectedCategoryUseCase,
    private val updateSelectedAccountUseCase: UpdateSelectedAccountUseCase,
    accId: String,
) : ViewModel() {

    private val _filterState = MutableStateFlow(
        FilterState(
            date = "",
            dateRangeType = DateRangeType.THIS_MONTH,
            selectedCategories = emptyList(),
            allAccounts = emptyList(),
            selectedAccounts = emptyList(),
            selectedTransactionTypes = emptyList(),
            showForward = true,
            showBackward = true,
            showDateFilter = false,
            showTypeFilter = false,
            todayIncluded = true,
        )
    )
    val filterState = _filterState.asStateFlow()

    init {
        if ("" == accId) {
            getSelectedTransactionTypesUseCase.invoke().onEach { types ->
                _filterState.update {
                    it.copy(
                        selectedTransactionTypes = types
                    )
                }
            }.launchIn(viewModelScope)
        }

        getAllAccountsUseCase.invoke().onEach { accounts ->
            _filterState.update {
                it.copy(allAccounts = accounts.map { acc ->
                    acc.toAccountUiModel(Amount(amount = acc.amount))
                })
            }
        }.launchIn(viewModelScope)

        getSelectedAccountUseCase.invoke(accId).onEach { accounts ->
            _filterState.update {
                it.copy(
                    selectedAccounts = accounts?.map { account ->
                        account.toAccountUiModel(Amount(amount = account.amount))
                    } ?: emptyList()
                )
            }
        }.launchIn(viewModelScope)

        if ("" == accId) {
            getSelectedCategoriesUseCase.invoke().onEach { categories ->
                _filterState.update {
                    it.copy(selectedCategories = categories)
                }
            }.launchIn(viewModelScope)
        }

        getDateRangeUseCase.invoke().onEach {
            val dateRangeType = it.type

            val date = if (it.type == DateRangeType.ALL) {
                it.name
            } else {
                it.description
            }
            var showForward = false
            var showBackward = false

            when (it.type) {
                DateRangeType.TODAY,
                DateRangeType.THIS_WEEK,
                DateRangeType.THIS_MONTH,
                DateRangeType.THIS_YEAR,
                -> {
                    showForward = true
                    showBackward = true
                }

                DateRangeType.ALL,
                DateRangeType.CUSTOM,
                -> {
                    showForward = false
                    showBackward = false
                }
            }

            _filterState.update { state ->
                state.copy(
                    showForward = showForward,
                    showBackward = showBackward,
                    dateRangeType = dateRangeType,
                    date = date,
                    todayIncluded = it.includeToday
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun moveDateRangeForward() {
        viewModelScope.launch {
            moveDateRangeForwardUseCase.invoke(_filterState.value.dateRangeType)
        }
    }

    private fun moveDateRangeBackward() {
        viewModelScope.launch {
            moveDateRangeBackwardUseCase.invoke(_filterState.value.dateRangeType)
        }
    }

    private fun removeTransaction(transactionType: TransactionType) {
        viewModelScope.launch {
            updateSelectedTransactionTypesUseCase.invoke(
                _filterState.value.selectedTransactionTypes.addOrRemove(transactionType),
            )
        }
    }

    private fun removeAccount(accountUiModel: AccountUiModel) {
        viewModelScope.launch {
            updateSelectedAccountUseCase.invoke(
                _filterState.value.selectedAccounts.addOrRemove(accountUiModel).map { it.id },
            )
        }
    }

    private fun removeCategory(category: Category) {
        viewModelScope.launch {
            updateSelectedCategoryUseCase.invoke(
                _filterState.value.selectedCategories.addOrRemove(category).map { it.id },
            )
        }
    }

    private fun updateFilterAccount(accId: String) {
        viewModelScope.launch {
            updateSelectedAccountUseCase.invoke(accId)
        }
    }

    fun processAction(action: FilterAction) {
        when (action) {
            FilterAction.MoveDateBackward -> moveDateRangeBackward()
            FilterAction.MoveDateForward -> moveDateRangeForward()
            is FilterAction.RemoveAccount -> removeAccount(action.account)
            is FilterAction.RemoveCategory -> removeCategory(action.category)
            is FilterAction.RemoveTransactionType -> removeTransaction(action.transactionType)
            is FilterAction.UpdateFilterAccount -> updateFilterAccount(action.accId)
            FilterAction.ShowDateFilter -> {
                _filterState.update { it.copy(showDateFilter = true) }
            }

            FilterAction.DismissDateFilter -> {
                _filterState.update { it.copy(showDateFilter = false) }
            }

            FilterAction.ShowTypeFilter -> {
                _filterState.update { it.copy(showTypeFilter = true) }
            }

            FilterAction.DismissTypeFilter -> {
                _filterState.update { it.copy(showTypeFilter = false) }
            }
        }
    }
}
