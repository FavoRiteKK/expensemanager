package com.naveenapps.expensemanager.core.common4mp

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSString
import platform.Foundation.currentLocale
import platform.Foundation.localizedStringWithFormat

actual fun platform() = "iOS"

actual abstract class LWNumberFormat : NSNumberFormatter() {
    actual fun format(number: Double): String? {
        return stringFromNumber(NSNumber(number))
    }

    actual fun format(number: Any?): String? {
        if (number is Double) {
            return format(number)
        }

        return null
    }
}

actual fun LWNumberFormat_getNumberInstance(): LWNumberFormat {
    val ans = object : LWNumberFormat() {}
    ans.locale = LWLocale_getDefault()
    return ans
}

actual fun LWString_format(
    s: String,
    vararg args: Any?
): String {
    return NSString.localizedStringWithFormat(s, *args)
}

actual typealias LWLocale = NSLocale

actual fun LWLocale_getDefault(): LWLocale = NSLocale.currentLocale()
