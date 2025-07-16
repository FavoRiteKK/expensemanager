package com.naveenapps.expensemanager.feature.analysis

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LayeredComponent
import com.patrykandpatrick.vico.multiplatform.common.component.LineComponent
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.fill
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape.Corner
import com.patrykandpatrick.vico.multiplatform.common.shape.DashedShape
import com.patrykandpatrick.vico.multiplatform.common.shape.MarkerCorneredShape

@Composable
internal fun rememberMarker(): CartesianMarker {
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    val labelBackground =
        rememberShapeComponent(
            fill = Fill(labelBackgroundColor),
            shape = labelBackgroundShape,
            strokeFill = Fill(MaterialTheme.colorScheme.outline),
            strokeThickness = 1.dp,
        )
    val label =
        rememberTextComponent(
            background = labelBackground,
            lineCount = LABEL_LINE_COUNT,
            padding = labelPadding,
            style =
                TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = FontFamily.Monospace,
                ),
        )

    return rememberDefaultCartesianMarker(
        label = label,
        indicator = { color ->
            val indicatorInnerComponent =
                ShapeComponent(Fill(color), CorneredShape.Pill)
            val indicatorCenterComponent = ShapeComponent(Fill(Color.White), CorneredShape.Pill)
            val indicatorOuterComponent = ShapeComponent(Fill(color), CorneredShape.Pill)

            LayeredComponent(
                back = indicatorOuterComponent,
                front = LayeredComponent(
                    back = indicatorCenterComponent,
                    front = indicatorInnerComponent,
                    padding = Insets(indicatorInnerAndCenterComponentPaddingValue),
                ),
                padding = Insets(indicatorCenterAndOuterComponentPaddingValue),
            )
        },
        guideline = LineComponent(
            fill = fill(MaterialTheme.colorScheme.onSurface.copy(0.2f)),
            thickness = guidelineThickness,
            shape = guidelineShape
        )
    )
}

private const val LABEL_LINE_COUNT = 1
private val GUIDELINE_DASH_LENGTH_DP = 8.dp
private val GUIDELINE_GAP_LENGTH_DP = 4.dp

private val labelBackgroundShape = MarkerCorneredShape(Corner.Rounded)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = Insets(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 5.dp
private val indicatorCenterAndOuterComponentPaddingValue = 10.dp
private val guidelineThickness = 2.dp
private val guidelineShape =
    DashedShape(CorneredShape.Pill, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)
