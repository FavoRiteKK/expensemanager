package com.naveenapps.expensemanager.core.testing4mp

import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

actual fun LWTruth_assertThat(actual: Any?): LWSubject {
    return LWSubjectImpl(actual)
}

actual fun LWTruth_assertThat(actual: Boolean?): LWBooleanSubject {
    return LWSubjectImpl(actual)
}

actual fun LWTruth_assertThat(actual: String?): LWStringSubject {
    return LWSubjectImpl(actual)
}

actual fun <E> LWTruth_assertThat(actual: Iterable<E>?): LWIterableSubject {
    return LWSubjectImpl(actual)
}

internal class LWSubjectImpl(private val actual: Any?) : LWSubject, LWBooleanSubject,
    LWStringSubject, LWIterableSubject {
    override fun isNull() {
        assertNull(actual)
    }

    override fun isNotNull() {
        assertNotNull(actual)
    }

    override fun isInstanceOf(clazz: KClass<*>) {
        assertTrue(clazz.isInstance(actual))
    }

    override fun isFalse() {
        assertTrue(actual == false)
    }

    override fun isTrue() {
        assertTrue(actual == true)
    }

    override fun isEqualTo(expected: Any?) {
        assertEquals(expected, actual)
    }

    override fun isEmpty() {
        when (actual) {
            is String -> assertTrue { actual.isEmpty() }
            is Iterable<*> -> assertTrue { actual.count() == 0 }
        }
    }

    override fun isNotEmpty() {
        when (actual) {
            is String -> assertTrue { actual.isNotEmpty() }
            is Iterable<*> -> assertTrue { actual.count() > 0 }
        }
    }

    override fun hasSize(size: Int) {
        assertTrue {
            actual is Iterable<*> && actual.count() == size
        }
    }
}
