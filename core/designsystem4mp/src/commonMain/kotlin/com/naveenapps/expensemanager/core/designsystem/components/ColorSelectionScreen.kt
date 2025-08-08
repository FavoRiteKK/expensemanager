package com.naveenapps.expensemanager.core.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.ui.components.SelectionTitle
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.ui.utils.ColorIconSpecModifier
import com.naveenapps.expensemanager.core.designsystem.ui.utils.toColorInt
import expensemanager.core.designsystem4mp.generated.resources.Res
import expensemanager.core.designsystem4mp.generated.resources.choose_color
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val colors = listOf(
    "#FFE57373",
    "#FFF44336",
    "#FFD32F2F",
    "#FFB71C1C",

    "#FFF06292",
    "#FFE91E63",
    "#FFC2185B",
    "#FF880E4F",

    "#FFBA68C8",
    "#FF9C27B0",
    "#FF7B1FA2",
    "#FF4A148C",

    "#FF9575CD",
    "#FF673AB7",
    "#FF512DA8",
    "#FF311B92",

    "#FF7986CB",
    "#FF3F51B5",
    "#FF303F9F",
    "#FF1A237E",

    "#FF64B5F6",
    "#FF2196F3",
    "#FF1976D2",
    "#FF0D47A1",

    "#FF4FC3F7",
    "#FF03A9F4",
    "#FF0288D1",
    "#FF01579B",

    "#FF4DD0E1",
    "#FF00BCD4",
    "#FF0097A7",
    "#FF006064",

    "#FF4DB6AC",
    "#FF009688",
    "#FF00796B",
    "#FF004D40",

    "#FF81C784",
    "#FF4CAF50",
    "#FF388E3C",
    "#FF1B5E20",

    "#FFAED581",
    "#FF8BC34A",
    "#FF689F38",
    "#FF33691E",

    "#FFDCE775",
    "#FFCDDC39",
    "#FFAFB42B",
    "#FF827717",

    "#FFFFF176",
    "#FFFFEB3B",
    "#FFFBC02D",
    "#FFF57F17",

    "#FFFFB74D",
    "#FFFF9800",
    "#FFF57C00",
    "#FFE65100",

    "#FFA1887F",
    "#FF795548",
    "#FF5D4037",
    "#FF3E2723",

    "#FF90A4AE",
    "#FF607D8B",
    "#FF455A64",
    "#FF263238",
)

@Composable
fun ColorSelectionScreen(onColorPicked: ((Int) -> Unit)? = null) {
    val columns = GridCells.Adaptive(minSize = 48.dp)
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        columns = columns,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item(span = {
            GridItemSpan(this.maxLineSpan)
        }) {
            SelectionTitle(
                stringResource(resource = Res.string.choose_color),
                Modifier.Companion
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
            )
        }
        items(colors, key = { it }) { color ->
            val parsedColor = color.toColorInt()
            Box(
                modifier = ColorIconSpecModifier
                    .clickable {
                        onColorPicked?.invoke(parsedColor)
                    },
            ) {
                Canvas(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .align(Alignment.Center),
                    onDraw = {
                        drawCircle(color = Color(parsedColor))
                    },
                )
            }
        }
        item(span = {
            GridItemSpan(this.maxLineSpan)
        }) {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Preview
@Composable
private fun ColorSelectionPreview() {
    ExpenseManagerTheme {
        ColorSelectionScreen()
    }
}
