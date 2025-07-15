package com.naveenapps.expensemanager.feature.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.domain4mp.usecase.settings.theme.GetCurrentThemeUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.transaction.GetAmountStateUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.transaction.GetAverageDataUseCase
import com.naveenapps.expensemanager.core.domain4mp.usecase.transaction.GetChartDataUseCase
import com.naveenapps.expensemanager.core.model4mp.ExpenseFlowState
import com.naveenapps.expensemanager.core.model4mp.AverageData
import com.naveenapps.expensemanager.core.model4mp.Theme
import com.naveenapps.expensemanager.core.model4mp.TransactionUiItem
import com.naveenapps.expensemanager.core.model4mp.WholeAverageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.naveenapps.expensemanager.core.data4mp.utils.LWAppCompatDelegate
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import expensemanager.feature.analysis4mp.generated.resources.Res
import expensemanager.feature.analysis4mp.generated.resources.analysis

class AnalysisScreenViewModel (
    getCurrentThemeUseCase: GetCurrentThemeUseCase,
    getChartDataUseCase: GetChartDataUseCase,
    getAverageDataUseCase: GetAverageDataUseCase,
    getAmountStateUseCase: GetAmountStateUseCase,
) : ViewModel() {

    private val _currentTheme = MutableStateFlow(
        Theme(
            LWAppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
            Res.string.analysis,
        ),
    )
    val currentTheme = _currentTheme.asStateFlow()

    private val _expenseFlowState = MutableStateFlow(ExpenseFlowState())
    val amountUiState = _expenseFlowState.asStateFlow()

    private val _transactionPeriod = MutableStateFlow("")
    val transactionPeriod = _transactionPeriod.asStateFlow()

    private val _graphItems = MutableStateFlow<AnalysisUiData?>(null)
    val graphItems = _graphItems.asStateFlow()

    private val _averageData = MutableStateFlow(
        WholeAverageData(
            AverageData(
                "0.00$",
                "0.00$",
                "0.00$",
            ),
            AverageData(
                "0.00$",
                "0.00$",
                "0.00$",
            ),
        ),
    )
    val averageData = _averageData.asStateFlow()

    init {
        getChartDataUseCase.invoke().onEach { response ->
            _graphItems.value = AnalysisUiData(
                transactions = response.transactions,
                chartData = response.chartData?.let { chart ->
                    AnalysisUiChartData(
                        chartData = CartesianChartModelProducer(
                            chart.chartData.map {
                                it.map { entry ->
                                    entryOf(
                                        entry.index,
                                        entry.total,
                                    )
                                }
                            },
                        ).getModel(),
                        dates = chart.dates,
                    )
                },
            )
        }.launchIn(viewModelScope)

        getAverageDataUseCase.invoke().onEach { response ->
            _averageData.value = response
        }.launchIn(viewModelScope)

        getAmountStateUseCase.invoke().onEach { response ->
            _expenseFlowState.value = response
        }.launchIn(viewModelScope)

        getCurrentThemeUseCase.invoke().onEach {
            _currentTheme.value = it
        }.launchIn(viewModelScope)
    }
}

data class AnalysisUiData(
    val transactions: List<TransactionUiItem>,
    val chartData: AnalysisUiChartData? = null,
)

data class AnalysisUiChartData(
    val chartData: ChartEntryModel,
    val dates: List<String>,
    val title: String? = null,
)
