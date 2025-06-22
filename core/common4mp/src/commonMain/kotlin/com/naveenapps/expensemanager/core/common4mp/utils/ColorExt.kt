package com.naveenapps.expensemanager.core.common4mp.utils

const val RED_500 = 0xF44336
const val GREEN_500 = 0x43A546

fun Double.getAmountTextColor() = if (this < 0) {
    RED_500
} else {
    GREEN_500
}

fun Int.toColorString(): String {
    val hex = (this and 0xFFFFFF).toString(16).uppercase()
    return "#${hex.padStart(6, '0')}"
}
