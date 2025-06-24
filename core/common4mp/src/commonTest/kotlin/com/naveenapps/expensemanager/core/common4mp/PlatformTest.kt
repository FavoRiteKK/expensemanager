package com.naveenapps.expensemanager.core.common4mp

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformTest {

    @BeforeTest
    fun before() {
        println("before")
        KotlinNull
    }

    @Test
    fun getPlatform() {
        assertEquals("Java Desktop", platform())
    }
}