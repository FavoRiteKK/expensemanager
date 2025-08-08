package com.naveenapps.expensemanager.core.repository

interface FirebaseSettingsRepository {

    fun getPrivacyURL(): String

    fun getTermsURL(): String

    fun getFeedbackEmail(): String
}