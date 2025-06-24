package com.naveenapps.expensemanager.core.data4mp.mappers

import com.naveenapps.expensemanager.core.data4mp.dto.CountryResponseDto
import com.naveenapps.expensemanager.core.data4mp.dto.CurrencyResponseDto
import com.naveenapps.expensemanager.core.model4mp.Country
import com.naveenapps.expensemanager.core.model4mp.Currency


fun CountryResponseDto.toDomainModel(): Country? {
    val currency = currencyResponseDto?.toDomainModel()

    currency ?: return null

    return Country(
        name = name ?: "",
        countryCode = countryCode ?: "",
        currencyCode = currencyCode ?: "",
        currency = currency
    )
}

fun CurrencyResponseDto.toDomainModel(): Currency {
    return Currency(
        name = name ?: "",
        symbol = symbol ?: ""
    )
}
