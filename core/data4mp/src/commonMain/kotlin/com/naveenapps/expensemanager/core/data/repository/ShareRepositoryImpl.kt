package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.repository.FirebaseSettingsRepository
import com.naveenapps.expensemanager.core.repository.PlatformRepository
import com.naveenapps.expensemanager.core.repository.ShareRepository

class ShareRepositoryImpl(
    private val firebaseSettingsRepository: FirebaseSettingsRepository,
    private val platformRepository: PlatformRepository,
) : ShareRepository {
    override fun sendEmail() {
        platformRepository.sendEmail(firebaseSettingsRepository.getFeedbackEmail())
    }

    override fun share() {
        platformRepository.share()
    }

    override fun openRateUs() {
        platformRepository.openRateUs()
    }

    override fun openPrivacy() {
        platformRepository.openWebPage(firebaseSettingsRepository.getPrivacyURL())
    }

    override fun openTerms() {
        platformRepository.openWebPage(firebaseSettingsRepository.getTermsURL())
    }
}