package com.naveenapps.expensemanager.core.testing4mp

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

interface LWIterableSubject : LWSubject {
    fun isEmpty()
    fun isNotEmpty()
    fun hasSize(size: Int)
}

expect fun LWTruth_assertThat(actual: Any?): LWSubject

expect fun LWTruth_assertThat(actual: Boolean?): LWBooleanSubject

expect fun <E> LWTruth_assertThat(actual: Iterable<E>): LWIterableSubject