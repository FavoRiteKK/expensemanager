package com.naveenapps.expensemanager.feature.reminder

data class ReminderState(
    val shouldShowRationale: Boolean,
    val notificationAllowed: Boolean,
)