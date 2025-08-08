package com.naveenapps.expensemanager.core.domain.usecase.transaction

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.common.utils.lastDayOfMonth
import com.naveenapps.expensemanager.core.common.utils.toCompleteDate
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetCurrencyUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.currency.GetFormattedAmountUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.filter.daterange.GetDateRangeUseCase
import com.naveenapps.expensemanager.core.model.AverageData
import com.naveenapps.expensemanager.core.model.DateRangeType
import com.naveenapps.expensemanager.core.model.WholeAverageData
import com.naveenapps.expensemanager.core.model.isExpense
import com.naveenapps.expensemanager.core.model.isIncome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn

class GetAverageDataUseCase(
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getFormattedAmountUseCase: GetFormattedAmountUseCase,
    private val getTransactionWithFilterUseCase: GetTransactionWithFilterUseCase,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val dispatcher: AppCoroutineDispatchers,
) {
    fun invoke(): Flow<WholeAverageData> {
        return combine(
            getCurrencyUseCase.invoke(),
            getDateRangeUseCase.invoke(),
            getTransactionWithFilterUseCase.invoke(),
        ) { currency, dateRangeModel, transactions ->
            val incomeAmount =
                transactions?.filter { it.type.isIncome() }?.sumOf { it.amount.amount } ?: 0.0
            val expenseAmount =
                transactions?.filter { it.type.isExpense() }?.sumOf { it.amount.amount } ?: 0.0

            val ranges = dateRangeModel.dateRanges

            val calendar = ranges[0].toCompleteDate()

            val daysMultiplier: Double
            val monthMultiplier: Double
            when (dateRangeModel.type) {
                DateRangeType.TODAY -> {
                    daysMultiplier = 1.0
                    monthMultiplier = calendar.lastDayOfMonth().toDouble()
                }

                DateRangeType.THIS_WEEK -> {
                    daysMultiplier = 1.0 / 7
                    monthMultiplier = 4.348
                }

                DateRangeType.THIS_MONTH -> {
                    daysMultiplier = 1.0 / calendar.lastDayOfMonth().toDouble()
                    monthMultiplier = 1.0
                }

                DateRangeType.THIS_YEAR,
                DateRangeType.CUSTOM,
                DateRangeType.ALL,
                    -> {
                    daysMultiplier = 1.0 / calendar.dayOfYear
                    monthMultiplier = 1.0 / 12
                }
            }
            WholeAverageData(
                expenseAverageData = AverageData(
                    perDay = getFormattedAmountUseCase.invoke(
                        (expenseAmount * daysMultiplier),
                        currency,
                    ).amountString.orEmpty(),
                    perMonth = getFormattedAmountUseCase.invoke(
                        (expenseAmount * monthMultiplier),
                        currency,
                    ).amountString.orEmpty(),
                ),
                incomeAverageData = AverageData(
                    perDay = getFormattedAmountUseCase.invoke(
                        (incomeAmount * daysMultiplier),
                        currency,
                    ).amountString.orEmpty(),
                    perMonth = getFormattedAmountUseCase.invoke(
                        (incomeAmount * monthMultiplier),
                        currency,
                    ).amountString.orEmpty(),
                ),
            )
        }.flowOn(dispatcher.computation)
    }
}
