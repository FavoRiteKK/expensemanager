package com.naveenapps.expensemanager.feature.transaction.list

import androidx.annotation.ColorInt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.common.utils.getAmountTextColor
import com.naveenapps.expensemanager.core.common.utils.toCompleteDateWithDate
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.account.GetSelectedAccountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.transaction.GetTransactionWithFilterUseCase
import com.naveenapps.expensemanager.core.model.Account
import com.naveenapps.expensemanager.core.model.Transaction
import com.naveenapps.expensemanager.core.model.TransactionGroup
import com.naveenapps.expensemanager.core.model.TransactionType
import com.naveenapps.expensemanager.core.model.TransactionUiItem
import com.naveenapps.expensemanager.core.model.toTransactionUIModel
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.navigation.ExpenseManagerScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update

class TransactionListViewModel(
    getSelectedAccountUseCase: GetSelectedAccountUseCase,
    getCurrencyUseCase: GetCurrencyUseCase,
    getFormattedAmountUseCase: GetFormattedAmountUseCase,
    getTransactionWithFilterUseCase: GetTransactionWithFilterUseCase,
    appCoroutineDispatchers: AppCoroutineDispatchers,
    private val appComposeNavigator: AppComposeNavigator,
    accId: String,
) : ViewModel() {

    private val _transactions = MutableStateFlow(TransactionListState(emptyList(), 0, ""))
    val state = _transactions.asStateFlow()

    init {
        combine(
            getCurrencyUseCase.invoke(),
            getTransactionWithFilterUseCase.invoke(accId),
            if (accId != "") {
                getSelectedAccountUseCase.invoke(accId)
            } else {
                flowOf<List<Account>?>(null)
            }
        ) { currency, transactions, filteredAccount ->

            val groupedItem = transactions?.groupBy {
                it.createdOn.toCompleteDateWithDate()
            }?.map {
                val totalAmount = it.value.toTransactionSum()
                TransactionGroup(
                    date = it.key,
                    amountTextColor = totalAmount.getAmountTextColor(),
                    totalAmount = getFormattedAmountUseCase.invoke(totalAmount, currency),
                    transactions = it.value.map { transaction ->
                        transaction.toTransactionUIModel(
                            getFormattedAmountUseCase.invoke(
                                transaction.amount.amount,
                                currency,
                            ),
                        )
                    },
                )
            }

            //default to first transaction
            val balance: String = filteredAccount?.first()?.amount?.let {
                getFormattedAmountUseCase.invoke(it, currency)
                    .amountString
            } ?: ""

            _transactions.update {
                it.copy(
                    transactionListItem = groupedItem?.convertGroupToTransactionListItems()
                        ?: emptyList(),
                    netBalance = balance
                )
            }
        }.flowOn(appCoroutineDispatchers.computation).launchIn(viewModelScope)
    }

    private fun openCreateScreen(transactionId: String? = null) {
        appComposeNavigator.navigate(
            ExpenseManagerScreens.TransactionCreate(transactionId),
        )
    }

    private fun closePage() {
        appComposeNavigator.popBackStack()
    }

    private fun checkBalanceUpTo(pos: Int) {
        _transactions.value.selectedPos.let { currPos ->
            if (currPos != pos) {
                val upward = currPos - pos > 0
                if (upward) {
                    calcNetWorthUpward(pos, currPos)
                } else {
                    calcNetWorthDownward(currPos, pos)
                }
                _transactions.update {
                    it.copy(selectedPos = pos)
                }
            }
        }
    }

    private fun calcNetWorthUpward(pos: Int, currPos: Int) {
//        val netBalance =
        _transactions.value.netBalance
        for (i in pos until currPos) {
            (_transactions.value.transactionListItem[i] as TransactionListItem.TransactionItem).item.amount.amount
            println("upward at $i")
        }
    }

    private fun calcNetWorthDownward(currPos: Int, newPos: Int) {
        for (i in newPos downTo currPos + 1) {
            println("downward at $i")
        }
    }

    fun processAction(action: TransactionListAction) {
        when (action) {
            TransactionListAction.ClosePage -> closePage()
            TransactionListAction.OpenCreateTransaction -> openCreateScreen()
            is TransactionListAction.OpenEdiTransaction -> openCreateScreen(action.transactionId)
            is TransactionListAction.BalanceAsLastTransaction -> checkBalanceUpTo(action.pos)
        }
    }
}

fun List<Transaction>.toTransactionSum() =
    this.sumOf {
        when (it.type) {
            TransactionType.INCOME -> {
                it.amount.amount
            }

            TransactionType.EXPENSE -> {
                it.amount.amount * -1
            }

            TransactionType.TRANSFER -> {
                0.0
            }
        }
    }

fun List<TransactionGroup>.convertGroupToTransactionListItems(): List<TransactionListItem> {
    return buildList {
        this@convertGroupToTransactionListItems.forEach {
            add(
                TransactionListItem.HeaderItem(
                    date = it.date,
                    amountTextColor = it.amountTextColor,
                    totalAmount = it.totalAmount.amountString ?: ""
                )
            )

            it.transactions.forEach {
                add(TransactionListItem.TransactionItem(item = it))
            }

            add(TransactionListItem.Divider)
        }
    }.mapIndexed { ix, item ->
        if (item is TransactionListItem.TransactionItem) {
            item.copy(item = item.item.copy(customPos = ix))
        } else {
            item
        }
    }
}

sealed class TransactionListItem {

    data class HeaderItem(
        val date: String,
        @ColorInt val amountTextColor: Int,
        val totalAmount: String,
    ) : TransactionListItem()

    data class TransactionItem(
        val item: TransactionUiItem,
    ) : TransactionListItem()

    data object Divider : TransactionListItem()
}
