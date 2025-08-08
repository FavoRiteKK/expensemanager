package com.naveenapps.expensemanager.core.repository

interface ShareRepository {

    fun sendEmail()

    fun share()

    fun openRateUs()

    fun openPrivacy()

    fun openTerms()
}