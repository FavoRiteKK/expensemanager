package com.naveenapps.expensemanager.core.designsystem.ui.components

import androidx.annotation.ColorInt
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.koalaplot.core.pie.DefaultSlice
import io.github.koalaplot.core.pie.PieChart
import io.github.koalaplot.core.pie.StraightLineConnector
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi

data class PieChartUiData(
    val name: String,
    val value: Float,
    @ColorInt val color: Int,
)

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun PieChartView(
    totalAmountText: String,
    chartData: List<PieChartUiData>,
    modifier: Modifier = Modifier,
    hasLabel: Boolean = false,
) {
    val colorCode = MaterialTheme.colorScheme.onBackground

    Crossfade(targetState = chartData, label = "") {
        PieChart(
            modifier = modifier.wrapContentSize(),
            values = chartData.map {
                it.value
            },
            slice = {
                DefaultSlice(Color(color = chartData[it].color))
            },
            label = { i ->
                if (hasLabel) Text(chartData[i].name)
            },
            labelConnector = { if (hasLabel) StraightLineConnector() },
            holeSize = 0.7f,
            holeContent = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    BasicText(
                        text = totalAmountText,
                        color = { colorCode },
                        softWrap = false,
                    )
                }
            },
            forceCenteredPie = true,
        )
    }
}
