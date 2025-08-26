package com.naveenapps.expensemanager.core.repository

interface PlatformRepository {
    val icLogReminderIdRes: Int

    fun openWebPage(url: String)
    fun sendEmail(email: String)
    fun share()
    fun openRateUs()
}