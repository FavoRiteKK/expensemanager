package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.repository.FirebaseSettingsRepository

class FirebaseSettingsRepositoryImpl : FirebaseSettingsRepository {
    override fun getPrivacyURL(): String {
        return "https://gist.github.com/FavoRiteKK/f8b7ab3d07209a2fb5bd2e80b6676d29"
    }

    override fun getTermsURL(): String {
        return "https://gist.github.com/FavoRiteKK/8c44f0a4566f8d7d059d7f58d7b25f78"
    }

    override fun getFeedbackEmail(): String {
        return "duong.ko.di.ko.toi@gmail.com"
    }
}