package com.naveenapps.expensemanager.core.model4mp

data class WholeAverageData(
    val expenseAverageData: AverageData,
    val incomeAverageData: AverageData,
)

data class AverageData(
    val perDay: String,
    val perWeek: String,
    val perMonth: String,
)
