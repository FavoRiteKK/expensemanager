package com.naveenapps.expensemanager.core.common

import com.naveenapps.expensemanager.core.common.utils.fromTimeAndHour
import com.naveenapps.expensemanager.core.common.utils.toTimeAndMinutesWithAMPM
import com.naveenapps.expensemanager.core.testing.LWTruth_assertThat
import kotlin.test.Test

class DateUtilsTest {
    @Test
    fun timeAsAmPm() {
        val expect = "06:59 AM"

        val actual = fromTimeAndHour(6, 59)
            .toTimeAndMinutesWithAMPM()

        LWTruth_assertThat(actual).isEqualTo(expect)
    }
}