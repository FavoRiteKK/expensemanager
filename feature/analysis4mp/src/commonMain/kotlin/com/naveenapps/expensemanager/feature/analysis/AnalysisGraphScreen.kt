package com.naveenapps.expensemanager.feature.analysis

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.common.utils.GREEN_500
import com.naveenapps.expensemanager.core.common.utils.RED_500
import com.naveenapps.expensemanager.core.designsystem.AppPreviews
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.components.AmountInfoWidget
import com.naveenapps.expensemanager.core.designsystem.components.DashboardWidgetTitle
import com.naveenapps.expensemanager.core.designsystem.components.EmptyItem
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getExpenseColor
import com.naveenapps.expensemanager.core.designsystem.ui.utils.getIncomeColor
import com.naveenapps.expensemanager.core.designsystem.utils.shouldUseDarkTheme
import com.naveenapps.expensemanager.core.model.AverageData
import com.naveenapps.expensemanager.core.model.ExpenseFlowState
import com.naveenapps.expensemanager.core.model.WholeAverageData
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.common.ProvideVicoTheme
import com.patrykandpatrick.vico.multiplatform.common.fill
import expensemanager.feature.analysis4mp.generated.resources.Res
import expensemanager.feature.analysis4mp.generated.resources.average_and_projected
import expensemanager.feature.analysis4mp.generated.resources.day
import expensemanager.feature.analysis4mp.generated.resources.month
import expensemanager.feature.analysis4mp.generated.resources.no_chart_available
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AnalysisGraphScreen(
    viewModel: AnalysisScreenViewModel = koinViewModel()
) {

    val graphData by viewModel.graphItems.collectAsState()
    val averageData by viewModel.averageData.collectAsState()
    val amountUiState by viewModel.amountUiState.collectAsState()
    val transactionPeriod by viewModel.transactionPeriod.collectAsState()

    val currentTheme by viewModel.currentTheme.collectAsState()
    val isDarkTheme = shouldUseDarkTheme(theme = currentTheme.mode)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        graphData?.chartData?.let {
            ChartScreen(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                chart = it,
                isDarkTheme = isDarkTheme,
            )
        } ?: run {
            EmptyItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                emptyItemText = stringResource(resource = Res.string.no_chart_available),
                icon = "ic_no_analysis"
            )
        }

        IncomeExpenseBalanceView(
            expenseFlowState = amountUiState,
            transactionPeriod = transactionPeriod,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        )
        TransactionAverageItem(
            modifier = Modifier.padding(16.dp),
            averageData = averageData,
        )
    }
}

@Composable
fun IncomeExpenseBalanceView(
    expenseFlowState: ExpenseFlowState,
    transactionPeriod: String,
    modifier: Modifier = Modifier,
) {
    AmountInfoWidget(
        expenseAmount = expenseFlowState.expense,
        incomeAmount = expenseFlowState.income,
        balanceAmount = expenseFlowState.balance,
        transactionPeriod = transactionPeriod,
        modifier = modifier,
    )
}

@Composable
fun TransactionAverageItem(
    averageData: WholeAverageData,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 1.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            DashboardWidgetTitle(title = stringResource(resource = Res.string.average_and_projected))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = stringResource(resource = Res.string.day),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                    )
                    Text(
                        text = stringResource(resource = Res.string.month),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Normal,
                    )
                }
                AverageAmountItems(
                    averageData = averageData.incomeAverageData,
                    textColor = Color(color = GREEN_500),
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                AverageAmountItems(
                    averageData = averageData.expenseAverageData,
                    textColor = Color(color = RED_500),
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterVertically),
                )
            }
        }
    }
}

@Composable
private fun AverageAmountItems(
    averageData: AverageData,
    modifier: Modifier = Modifier,
    textColor: Color = Color(color = GREEN_500),
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.End),
            text = averageData.perDay,
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.End,
        )
        Text(
            modifier = Modifier.align(Alignment.End),
            text = averageData.perMonth,
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.End,
        )
    }
}

@Composable
fun ChartScreen(
    chart: AnalysisUiChartData?,
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier,
) {
    chart ?: return

    val expenseColor = getExpenseColor()
    val incomeColor = getIncomeColor()
    val marker = rememberMarker()
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(chart) {
        modelProducer.runTransaction {
            lineSeries {
                chart.chartData.onEach { line ->
                    series(line.keys, line.values)
                }
            }
        }
    }
    ProvideVicoTheme(rememberChartTheme(chartColors, isDarkTheme)) {
        CartesianChartHost(
            modifier = modifier,
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(expenseColor)),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill = fill(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            expenseColor.copy(alpha = 0.5f),
                                            expenseColor.copy(alpha = 0f)
                                        )
                                    )
                                )
                            )
                        ),
                        LineCartesianLayer.rememberLine(
                            fill = LineCartesianLayer.LineFill.single(fill(incomeColor)),
                            areaFill = LineCartesianLayer.AreaFill.single(
                                fill = fill(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            incomeColor.copy(alpha = 0.5f),
                                            incomeColor.copy(alpha = 0f)
                                        )
                                    )
                                )
                            )
                        ),
                    )
                ),
                startAxis = VerticalAxis.rememberStart(),
                bottomAxis = HorizontalAxis.rememberBottom(),
                marker = marker
            ),
            modelProducer = modelProducer,
        )
    }
}

@AppPreviews
@Composable
fun ChartScreenPreview() {
    ExpenseManagerTheme {
        ChartScreen(
            isDarkTheme = true,
            chart = AnalysisUiChartData(
                chartData = listOf(
                    mapOf(
                        Pair(0, 1.0),
                        Pair(1, 2.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                    ),
                    mapOf(
                        Pair(0, 4.0),
                        Pair(1, 3.0),
                        Pair(2, 2.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                        Pair(2, 3.0),
                    ),
                ),
                dates = listOf(
                    "08/09",
                    "18/09",
                    "21/09",
                    "24/09",
                    "28/09",
                    "18/09",
                    "21/09",
                    "24/09",
                    "28/09",
                    "28/09",
                ),
            ),
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun TransactionAverageItemPreview() {
    ExpenseManagerTheme {
        TransactionAverageItem(
            WholeAverageData(
                AverageData(
                    "10.0$",
                    "10.0$",
                ),
                AverageData(
                    "10.0$",
                    "10.0$",
                ),
            ),
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun AmountSummaryPreview() {
    val AMOUNT_VALUE = "1000000"
    ExpenseManagerTheme {
        AmountInfoWidget(
            expenseAmount = AMOUNT_VALUE,
            incomeAmount = AMOUNT_VALUE,
            balanceAmount = AMOUNT_VALUE,
            transactionPeriod = "This Month(Oct 2023)",
        )
    }
}
