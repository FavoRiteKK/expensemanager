package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.Country


interface CountryRepository {

    suspend fun readCountries(): List<Country>
}