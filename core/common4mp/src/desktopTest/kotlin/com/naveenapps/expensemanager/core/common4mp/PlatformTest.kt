package com.naveenapps.expensemanager.core.common4mp

import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformTest {
    @Test
    fun getPlatform() {
        assertEquals("Java Desktop", platform())
    }
}