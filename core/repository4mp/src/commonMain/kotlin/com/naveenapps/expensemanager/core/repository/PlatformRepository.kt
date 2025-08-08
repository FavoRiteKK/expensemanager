package com.naveenapps.expensemanager.core.repository

interface PlatformRepository {
    fun openWebPage(url: String)
    fun sendEmail(email: String)
    fun share()
    fun openRateUs()
}