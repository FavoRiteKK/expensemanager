package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.LWFile
import com.naveenapps.expensemanager.core.repository.FirebaseSettingsRepository
import com.naveenapps.expensemanager.core.repository.ShareRepository

class ShareRepositoryImpl(
    private val firebaseSettingsRepository: FirebaseSettingsRepository
) : ShareRepository {
    override fun sendEmail(file: LWFile?) {
        TODO("Not yet implemented")
    }

    override fun share(file: LWFile?) {
        TODO("Not yet implemented")
    }

    override fun print(file: LWFile?) {
        TODO("Not yet implemented")
    }

    override fun openRateUs() {
        TODO("Not yet implemented")
    }

    override fun openPrivacy() {
        TODO("Not yet implemented")
    }

    override fun openTerms() {
        TODO("Not yet implemented")
    }

    override fun openAboutUs() {
        TODO("Not yet implemented")
    }

    override fun openGithub() {
        TODO("Not yet implemented")
    }

    override fun openInstagram() {
        TODO("Not yet implemented")
    }

    override fun openTwitter() {
        TODO("Not yet implemented")
    }

}