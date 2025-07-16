package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.repository.JsonConverterRepository
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

class JsonConverterRepositoryImpl(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
) : JsonConverterRepository {

    @OptIn(InternalSerializationApi::class)
    override suspend fun fromJsonToObject(
        value: String,
        classValue: KClass<*>
    ): Any? =
        withContext(appCoroutineDispatchers.io) {
            return@withContext runCatching {
                Json.decodeFromString(classValue.serializer(), value)
            }.onFailure {
                println("Countries" + (it.message ?: ""))
            }.getOrNull()
        }

    override suspend fun fromObjectToJson(value: Any): String? =
        withContext(appCoroutineDispatchers.io) {
            return@withContext runCatching {
                Json.encodeToString(value)
            }.onFailure {
                println("Countries" + (it.message ?: ""))
            }.getOrNull()
        }
}
