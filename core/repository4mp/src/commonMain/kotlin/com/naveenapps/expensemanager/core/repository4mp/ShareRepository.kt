package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.common4mp.LWFile

interface ShareRepository {

    fun sendEmail(file: LWFile?)

    fun share(file: LWFile?)

    fun print(file: LWFile?)

    fun openRateUs()

    fun openPrivacy()

    fun openTerms()

    fun openAboutUs()

    fun openGithub()

    fun openInstagram()

    fun openTwitter()
}