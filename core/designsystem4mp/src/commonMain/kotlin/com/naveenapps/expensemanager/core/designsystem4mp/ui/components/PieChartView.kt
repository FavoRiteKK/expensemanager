package com.naveenapps.expensemanager.core.designsystem4mp.ui.components

import androidx.annotation.ColorInt
import androidx.compose.animation.Crossfade
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

data class PieChartUiData(
    val name: String,
    val value: Float,
    @ColorInt val color: Int,
)

@Composable
fun PieChartView(
    totalAmountText: String,
    chartData: List<PieChartUiData>,
    modifier: Modifier = Modifier,
    chartHeight: Int = 600,
    hideValues: Boolean = false,
    chartWidth: Int = -1,    //MATCH_PARENT
) {
    var isAnimated by remember { mutableStateOf(false) }

    val colorCode = MaterialTheme.colorScheme.onBackground.hashCode()
    val holeColor = MaterialTheme.colorScheme.background.hashCode()

    Crossfade(targetState = chartData, label = "") { pieChartData ->
        // on below line we are creating an
        // android view for pie chart.
        Text("Not implemented yet")
    }
}
