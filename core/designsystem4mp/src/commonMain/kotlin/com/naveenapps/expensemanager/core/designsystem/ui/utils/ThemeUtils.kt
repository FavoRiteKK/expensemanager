package com.naveenapps.expensemanager.core.designsystem.ui.utils

import androidx.annotation.ColorInt
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.naveenapps.expensemanager.core.common.utils.BLACK_100
import com.naveenapps.expensemanager.core.common.utils.GREEN_500
import com.naveenapps.expensemanager.core.common.utils.RED_500

/**
 * @return Color int value from string of prefix '#'. Default to black if parsing failed
 */
@OptIn(ExperimentalStdlibApi::class)
@ColorInt
fun getColorValue(colorValue: String?): Int {
    return runCatching {
        //input color can be #RRGGBB or #AARRGGBB, we set to use latter one
        val color = if (colorValue?.count() == 7) {
            "#FF${colorValue.drop(1)}"
        } else {
            colorValue
        }

        val format = HexFormat { number.prefix = "#" }
        color?.hexToInt(format)
    }.getOrNull() ?: Color.Black.value.toInt()
}

/**
 * @return Color int value from string of prefix '#'. Default to black if parsing failed
 */
@ColorInt
fun String.toColorInt() = getColorValue(this)

fun String.toColor() = Color(getColorValue(this))

@Composable
@ColorInt
fun getIncomeColor() = Color(GREEN_500)

@ColorInt
@Composable
fun getExpenseColor() = Color(RED_500)

@ColorInt
@Composable
fun getBalanceColor() = Color(BLACK_100)

@ColorInt
@Composable
fun getIncomeBGColor() = getIncomeColor().copy(alpha = .1f)

@ColorInt
@Composable
fun getExpenseBGColor() = getExpenseColor().copy(alpha = .1f)

@ColorInt
@Composable
fun getBalanceBGColor() = getBalanceColor()

@ColorInt
@Composable
fun getSelectedBGColor() = getIncomeColor().copy(alpha = .1f)
