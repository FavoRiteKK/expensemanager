package com.naveenapps.expensemanager.core.common.utils

import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.toPercentString(): String {
    if (this.isNaN() || this.isInfinite()) return this.toString()
    val intValue = (this * 100).roundToInt()
    val integerPart = intValue / 100
    val fractionalPart = kotlin.math.abs(intValue % 100) // abs for negative numbers
        .toString().padStart(2, '0')
    return "$integerPart.${fractionalPart} %"
}

fun String.toCapitalize() = this.replaceFirstChar { char ->
    if (char.isLowerCase()) {
        char.titlecase()
    } else {
        char.toString()
    }
}

fun Enum<*>.toCapitalize() = this.toString().lowercase().toCapitalize()


/**
 * Formats a Double to a String with exactly one decimal place.
 * Handles rounding and ensures '.0' for whole numbers.
 */
fun Double.formatToOneDecimalPlace(): String {
    if (this.isNaN() || this.isInfinite()) return this.toString()

    // Multiply by 10, round to nearest int, then divide by 10.0 to get one decimal place
    val roundedValue = (this * 10).roundToInt() / 10.0

    val integerPart = roundedValue.toInt() // Get the integer part
    // Get the first decimal digit. Multiply by 10, take modulo 10.
    // abs is important for negative numbers.
    val decimalDigit = kotlin.math.abs((roundedValue * 10).roundToInt() % 10)

    return "$integerPart.$decimalDigit"
}

fun getCompactNumber(number: Number): String {
    val suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
    val numValue = number.toLong()
    val value = floor(log10(numValue.toDouble())).toInt()
    val base = value / 3
    return if (value >= 3 && base < suffix.size) {
        val scaledValue = numValue / 10.0.pow((base * 3).toDouble()) // Use kotlin.math.pow
        // Now use our common formatter
        scaledValue.formatToOneDecimalPlace() + suffix[base]
    } else {
        numValue.toString()
    }
}

fun String?.toDoubleOrNullWithLocale(): Double? {
    this ?: return null

    return try {
        this.toDoubleOrNull() ?: 0.0
    } catch (e: Exception) {
        null
    }
}

fun Double.toStringWithLocale(): String {
    return try {
        return this.toString()
    } catch (e: Exception) {
        "0.0"
    }
}
