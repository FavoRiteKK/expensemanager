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

actual fun log(message: String?) {
    println(generateTag() + "\t]" + message)
}

private fun generateTag(): String {
    val stackTraceElement = Thread.currentThread().stackTrace[4]
    val callerClazzName = stackTraceElement.className.run {
//        substring(lastIndexOf(".") + 1)
    }
    val tag = "$callerClazzName:${stackTraceElement.lineNumber}#${stackTraceElement.methodName}".ifEmpty { "" }
    return tag
}
