package com.naveenapps.expensemanager.core.domain4mp.usecase.budget

import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.common4mp.utils.GREEN_500
import com.naveenapps.expensemanager.core.common4mp.utils.LIGHT_GREEN_500
import com.naveenapps.expensemanager.core.common4mp.utils.ORANGE_500
import com.naveenapps.expensemanager.core.common4mp.utils.RED_500
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.transaction.GetTransactionWithFilterUseCase
import com.naveenapps.expensemanager.core.model4mp.Amount
import com.naveenapps.expensemanager.core.model4mp.Budget
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.model4mp.TransactionUiItem
import com.naveenapps.expensemanager.core.model4mp.isExpense
import com.naveenapps.expensemanager.core.model4mp.toTransactionUIModel
import com.naveenapps.expensemanager.core.repository4mp.BudgetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetBudgetsUseCase(
    private val budgetRepository: BudgetRepository,
    private val getTransactionWithFilterUseCase: GetTransactionWithFilterUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getFormattedAmountUseCase: GetFormattedAmountUseCase,
    private val getBudgetTransactionsUseCase: GetBudgetTransactionsUseCase,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) {
    operator fun invoke(): Flow<List<BudgetUiModel>> {
        return combine(
            getCurrencyUseCase.invoke(),
            getTransactionWithFilterUseCase.invoke(),
            budgetRepository.getBudgets(),
        ) { currency, _, budgets ->
            budgets.map {
                val transactions = when (val response = getBudgetTransactionsUseCase.invoke(it)) {
                    is Resource.Error -> {
                        null
                    }

                    is Resource.Success -> {
                        response.data.filter {
                            it.type.isExpense()
                        }
                    }
                }
                val transactionAmount = transactions?.sumOf { it.amount.amount } ?: 0.0
                val percent = (transactionAmount / it.amount).toFloat() * 100
                it.toBudgetUiModel(
                    budgetAmount = getFormattedAmountUseCase(it.amount, currency),
                    transactionAmount = getFormattedAmountUseCase(transactionAmount, currency),
                    percent,
                    transactions?.map {
                        it.toTransactionUIModel(
                            getFormattedAmountUseCase(it.amount.amount, currency),
                        )
                    },
                )
            }
        }.flowOn(appCoroutineDispatchers.computation)
    }
}

fun Budget.toBudgetUiModel(
    budgetAmount: Amount,
    transactionAmount: Amount,
    percent: Float,
    transactions: List<TransactionUiItem>? = null,
) = BudgetUiModel(
    id = this.id,
    name = this.name,
    icon = this.storedIcon.name,
    iconBackgroundColor = this.storedIcon.backgroundColor,
    progressBarColor = when {
        percent < 0f -> GREEN_500
        percent in 0f..35f -> GREEN_500
        percent in 36f..60f -> LIGHT_GREEN_500
        percent in 61f..85f -> ORANGE_500
        else -> RED_500
    },
    amount = budgetAmount,
    transactionAmount = transactionAmount,
    percent = percent,
    transactions = transactions,
)

data class BudgetUiModel(
    val id: String,
    val name: String,
    val icon: String,
    val iconBackgroundColor: String,
    val progressBarColor: Int,
    val amount: Amount,
    val transactionAmount: Amount,
    val percent: Float,
    val transactions: List<TransactionUiItem>? = null,
)
