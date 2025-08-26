package com.naveenapps.expensemanager.core.common

expect fun platform(): String

expect abstract class LWNumberFormat {
    fun format(number: Double): String
    fun format(number: Any?): String
}

expect class LWLocale

expect fun LWString_format(s: String, vararg args: Any?): String

expect fun LWNumberFormat_getNumberInstance(): LWNumberFormat

expect fun LWLocale_getDefault(): LWLocale
