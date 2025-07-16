package com.naveenapps.expensemanager.core.testing

import com.google.common.truth.BooleanSubject
import com.google.common.truth.IterableSubject
import com.google.common.truth.StringSubject
import com.google.common.truth.Subject
import com.google.common.truth.Truth
import kotlin.reflect.KClass
import kotlin.test.assertTrue

actual fun LWTruth_assertThat(actual: Any?): LWSubject {
    if (actual is Throwable)
        return LWSubjectImpl(Truth.assertThat(actual), actual)

    return LWSubjectImpl(Truth.assertThat(actual), actual)
}

actual fun LWTruth_assertThat(actual: Boolean?): LWBooleanSubject {
    return LWBooleanSubjectImpl(Truth.assertThat(actual), actual)
}

actual fun LWTruth_assertThat(actual: String?): LWStringSubject {
    return LWStringSubjectImpl(Truth.assertThat(actual), actual)
}

actual fun <E> LWTruth_assertThat(actual: Iterable<E>?): LWIterableSubject {
    return LWIterableSubjectImpl(Truth.assertThat(actual), actual)
}

internal open class LWSubjectImpl(protected val subject: Subject, private val actual: Any?) :
    LWSubject {
    override fun isNull() {
        subject.isNull()
    }

    override fun isNotNull() {
        subject.isNotNull()
    }

    override fun isInstanceOf(clazz: KClass<*>) {
        assertTrue(clazz.isInstance(actual))
    }

    override fun isEqualTo(expected: Any?) {
        subject.isEqualTo(expected)
    }
}

internal class LWBooleanSubjectImpl(private val subject: BooleanSubject, actual: Boolean?) :
    LWBooleanSubject, LWSubject by LWSubjectImpl(subject, actual) {

    override fun isFalse() {
        subject.isFalse()
    }

    override fun isTrue() {
        subject.isTrue()
    }
}

internal class LWStringSubjectImpl(private val subject: StringSubject, actual: String?) :
    LWStringSubject, LWSubject by LWSubjectImpl(subject, actual) {
    override fun isEmpty() {
        subject.isEmpty()
    }

    override fun isNotEmpty() {
        subject.isNotEmpty()
    }
}

internal class LWIterableSubjectImpl(private val subject: IterableSubject, actual: Iterable<*>?) :
    LWIterableSubject, LWSubject by LWSubjectImpl(subject, actual) {
    override fun isEmpty() {
        subject.isEmpty()
    }

    override fun isNotEmpty() {
        subject.isNotEmpty()
    }

    override fun hasSize(size: Int) {
        subject.hasSize(size)
    }
}
