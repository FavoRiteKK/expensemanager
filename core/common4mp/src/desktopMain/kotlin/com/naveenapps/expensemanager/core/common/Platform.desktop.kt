package com.naveenapps.expensemanager.core.common

import java.text.NumberFormat
import java.util.Locale

actual fun platform(): String = "Java Desktop"

actual typealias LWNumberFormat = NumberFormat
actual typealias LWLocale = Locale

actual fun LWNumberFormat_getNumberInstance(): LWNumberFormat =
    NumberFormat.getInstance(LWLocale_getDefault())

actual fun LWString_format(
    s: String,
    vararg args: Any?
): String {
    return String.format(s, *args)
}

actual fun LWLocale_getDefault(): LWLocale = Locale.getDefault()
