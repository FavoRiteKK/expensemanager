package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.data.dto.CountriesResponseDto
import com.naveenapps.expensemanager.core.data.mappers.toDomainModel
import com.naveenapps.expensemanager.core.data.utils.convertFileToString
import com.naveenapps.expensemanager.core.model.Country
import com.naveenapps.expensemanager.core.repository.CountryRepository
import com.naveenapps.expensemanager.core.repository.JsonConverterRepository
import kotlinx.coroutines.withContext

/**
 * Repository Implementation. Which carries the information about how we are reading the countries
 * information from the json files.
 */
class CountryRepositoryImpl(
    private val jsonConverterRepository: JsonConverterRepository,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : CountryRepository {

    override suspend fun readCountries(): List<Country> = withContext(appCoroutineDispatchers.io) {
        return@withContext convertFileToString(fileName = "countries.json")
            .let { jsonString ->
                return@let (jsonConverterRepository.fromJsonToObject(
                    jsonString,
                    CountriesResponseDto::class
                ) as? CountriesResponseDto)?.counties?.mapNotNull {
                    it.toDomainModel()
                }
            } ?: emptyList()
    }
}