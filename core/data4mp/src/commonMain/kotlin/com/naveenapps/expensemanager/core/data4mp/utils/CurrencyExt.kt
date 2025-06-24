package com.naveenapps.expensemanager.core.data4mp.utils

import com.naveenapps.expensemanager.core.common4mp.LWNumberFormat_getNumberInstance
import com.naveenapps.expensemanager.core.common4mp.LWString_format
import com.naveenapps.expensemanager.core.common4mp.utils.toDoubleOrNullWithLocale
import com.naveenapps.expensemanager.core.model4mp.Currency
import com.naveenapps.expensemanager.core.model4mp.TextFormat
import com.naveenapps.expensemanager.core.model4mp.TextPosition

fun getCurrency(
    currency: Currency,
    amount: Double,
): String {

    val reduceDigitFormat = "%.1f"
    val currencyFormatted = when (currency.format) {
        TextFormat.NONE -> LWString_format(reduceDigitFormat, amount)
        TextFormat.NUMBER_FORMAT -> {
            LWNumberFormat_getNumberInstance().format(
                LWString_format(
                    reduceDigitFormat,
                    amount,
                ).toDoubleOrNullWithLocale(),
            )
        }
    }

    return when (currency.position) {
        TextPosition.PREFIX -> {
            LWString_format(
                "%s%s",
                currency.symbol,
                currencyFormatted,
            )
        }

        TextPosition.SUFFIX -> {
            LWString_format(
                "%s%s",
                currencyFormatted,
                currency.symbol,
            )
        }
    }
}
