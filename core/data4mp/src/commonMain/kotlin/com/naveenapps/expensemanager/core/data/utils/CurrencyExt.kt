package com.naveenapps.expensemanager.core.data.utils

import com.naveenapps.expensemanager.core.common.LWNumberFormat_getNumberInstance
import com.naveenapps.expensemanager.core.common.LWString_format
import com.naveenapps.expensemanager.core.common.utils.toDoubleOrNullWithLocale
import com.naveenapps.expensemanager.core.common.utils.toSimpleString
import com.naveenapps.expensemanager.core.model.Currency
import com.naveenapps.expensemanager.core.model.TextFormat
import com.naveenapps.expensemanager.core.model.TextPosition

fun getCurrency(
    currency: Currency,
    amount: Double,
): String {
    val currencyFormatted = when (currency.format) {
        TextFormat.NONE -> amount.toSimpleString()
        TextFormat.NUMBER_FORMAT -> {
            LWNumberFormat_getNumberInstance().format(amount)
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

fun getNumberFormat(
    currency: Currency,
    amount: String,
    prefix: String = "= ",
): String {

    return prefix + when (currency.format) {
        TextFormat.NONE -> amount
        TextFormat.NUMBER_FORMAT -> {
            LWNumberFormat_getNumberInstance().format(
                amount.toDoubleOrNullWithLocale(),
            )
        }
    }
}
