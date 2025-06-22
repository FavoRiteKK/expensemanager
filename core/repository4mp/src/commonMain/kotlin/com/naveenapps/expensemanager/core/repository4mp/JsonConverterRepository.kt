package com.naveenapps.expensemanager.core.repository4mp

import kotlin.reflect.KClass

interface JsonConverterRepository {

    suspend fun fromJsonToObject(value: String, classValue: KClass<*>): Any?

    suspend fun fromObjectToJson(value: Any): String?
}
