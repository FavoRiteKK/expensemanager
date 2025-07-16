package com.naveenapps.expensemanager.core.testing

import kotlin.reflect.KClass

interface LWSubject {
    fun isNull()
    fun isNotNull()
    fun isInstanceOf(value: KClass<*>)
    fun isEqualTo(expected: Any?)
}

interface LWBooleanSubject : LWSubject {
    fun isFalse()
    fun isTrue()
}

interface LWStringSubject : LWSubject {
    fun isEmpty()
    fun isNotEmpty()
}

interface LWIterableSubject : LWSubject {
    fun isEmpty()
    fun isNotEmpty()
    fun hasSize(size: Int)
}

expect fun LWTruth_assertThat(actual: Any?): LWSubject

expect fun LWTruth_assertThat(actual: Boolean?): LWBooleanSubject

expect fun LWTruth_assertThat(actual: String?): LWStringSubject

expect fun <E> LWTruth_assertThat(actual: Iterable<E>?): LWIterableSubject