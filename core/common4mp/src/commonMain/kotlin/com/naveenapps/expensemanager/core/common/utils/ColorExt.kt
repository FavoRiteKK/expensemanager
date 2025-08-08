package com.naveenapps.expensemanager.core.common.utils

const val RED_500 = 0xFFF44336.toInt()
const val GREEN_500 = 0xFF43A546.toInt()
const val LIGHT_GREEN_500 = 0xFFCDDC39.toInt()
const val ORANGE_500 = 0xFFFF9800.toInt()
const val BLACK_100 = 0x10000000    //below 0x80xxxxxx is Int
const val BLUE_500 = 0xFF166EF7.toInt()

fun Double.getAmountTextColor() = if (this < 0) {
    RED_500
} else {
    GREEN_500
}.toInt()

fun Int.toColorString(): String {
    val hex = (this.toLong() and 0xFFFFFFFF).toString(16).uppercase()
    return "#${hex.padStart(8, '0')}"
}
