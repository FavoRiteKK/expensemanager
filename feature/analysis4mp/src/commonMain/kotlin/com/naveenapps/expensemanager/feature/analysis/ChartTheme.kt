package com.naveenapps.expensemanager.feature.analysis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.patrykandpatrick.vico.multiplatform.common.DefaultColors
import com.patrykandpatrick.vico.multiplatform.common.VicoTheme

@Composable
internal fun rememberChartTheme(
    columnChartColors: List<Color>,
    lineChartColors: List<Color>,
    isSystemInDarkTheme: Boolean,
): VicoTheme {
    return remember(columnChartColors, lineChartColors, isSystemInDarkTheme) {
        val defaultColors = if (isSystemInDarkTheme) DefaultColors.Dark else DefaultColors.Light
        VicoTheme(
            candlestickCartesianLayerColors = VicoTheme.CandlestickCartesianLayerColors.fromDefaultColors(
                defaultColors
            ),
            columnCartesianLayerColors = columnChartColors,
            lineCartesianLayerColors = lineChartColors,
            lineColor = Color(defaultColors.lineColor),
            textColor = Color(defaultColors.textColor),
        )
    }
}

val chartColors = listOf<Color>()

@Composable
internal fun rememberChartTheme(
    chartColors: List<Color>,
    isSystemInDarkTheme: Boolean,
) = rememberChartTheme(
    columnChartColors = chartColors,
    lineChartColors = chartColors,
    isSystemInDarkTheme,
)
