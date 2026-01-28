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
import com.naveenapps.expensemanager.core.model.Amount
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
    getTransactionWithFilterUseCase: GetTransactionWithFilterUseCase,
    appCoroutineDispatchers: AppCoroutineDispatchers,
    private val getFormattedAmountUseCase: GetFormattedAmountUseCase,
    private val appComposeNavigator: AppComposeNavigator,
    accId: String,
) : ViewModel() {

    private val _transactions = MutableStateFlow(
        TransactionListState(
            transactionListItem = emptyList(),
            selectedPos = 0,
            netBalanceString = ""
        )
    )
    val state = _transactions.asStateFlow()
    private lateinit var byAccount: ByAccount

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
            filteredAccount?.first()?.let {
                val amount = getFormattedAmountUseCase.invoke(it.amount, currency)
                byAccount = ByAccount(account = it, netAmount = amount)
                amount
            }.let { amount ->
                if (null != amount) {
                    _transactions.update {
                        it.copy(
                            transactionListItem = groupedItem?.convertGroupToTransactionListItems()
                                ?: emptyList(),
                            netBalanceString = amount.amountString ?: ""
                        )
                    }
                } else {
                    _transactions.update {
                        it.copy(
                            transactionListItem = groupedItem?.convertGroupToTransactionListItems()
                                ?: emptyList(),
                        )
                    }
                }
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
                val netBalanceAtPos = if (upward) {
                    calcNetWorthUpward(pos, currPos)
                } else {
                    calcNetWorthDownward(currPos, pos)
                }

                byAccount = byAccount.copy(netAmount = netBalanceAtPos)
                netBalanceAtPos.currency?.let { currency ->
                    getFormattedAmountUseCase.invoke(netBalanceAtPos.amount, currency)
                }?.amountString?.let { amnStr ->
                    _transactions.update {
                        it.copy(netBalanceString = amnStr, selectedPos = pos)
                    }
                }
            }
        }
    }

    private fun calcNetWorthUpward(newPos: Int, currPos: Int): Amount {
        var newByAccount = byAccount
        for (i in newPos until currPos) {
            _transactions.value.transactionListItem[i].let {
                it as? TransactionListItem.TransactionItem
            }?.item?.let {
                newByAccount = when (it.transactionType) {
                    TransactionType.INCOME -> {
                        newByAccount.addTransactionAmount(it)
                    }
                    TransactionType.EXPENSE -> {
                        newByAccount.subtractTransactionAmount(it)
                    }

                    TransactionType.TRANSFER -> {
                        if (it.fromAccountId == newByAccount.account.id) {
                            newByAccount.subtractTransactionAmount(it)
                        } else {
                            newByAccount.addTransactionAmount(it)
                        }
                    }
                }
            }
        }

        return newByAccount.netAmount
    }

    private fun calcNetWorthDownward(currPos: Int, newPos: Int): Amount {
        var newByAccount = byAccount
        for (i in currPos until newPos) {
            _transactions.value.transactionListItem[i].let {
                it as? TransactionListItem.TransactionItem
            }?.item?.let {
                newByAccount = when (it.transactionType) {
                    TransactionType.INCOME -> {
                        newByAccount.subtractTransactionAmount(it)
                    }
                    TransactionType.EXPENSE -> {
                        newByAccount.addTransactionAmount(it)
                    }

                    TransactionType.TRANSFER -> {
                        if (it.fromAccountId == newByAccount.account.id) {
                            newByAccount.addTransactionAmount(it)
                        } else {
                            newByAccount.subtractTransactionAmount(it)
                        }
                    }
                }
            }
        }

        return newByAccount.netAmount
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

private data class ByAccount(
    val account: Account,
    val netAmount: Amount
)

private fun ByAccount.subtractTransactionAmount(
    item: TransactionUiItem
): ByAccount {
    val newAmount =
        (netAmount.amount - item.amount.amount).let { newAmount ->
            netAmount.copy(amount = newAmount)
        }
    return this.copy(netAmount = newAmount)
}

private fun ByAccount.addTransactionAmount(
    item: TransactionUiItem
): ByAccount {
    val newAmount =
        (netAmount.amount + item.amount.amount).let { newAmount ->
            netAmount.copy(amount = newAmount)
        }
    return this.copy(netAmount = newAmount)
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
