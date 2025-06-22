package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.common4mp.File4Mp

interface ShareRepository {

    fun sendEmail(file: File4Mp?)

    fun share(file: File4Mp?)

    fun print(file: File4Mp?)

    fun openRateUs()

    fun openPrivacy()

    fun openTerms()

    fun openAboutUs()

    fun openGithub()

    fun openInstagram()

    fun openTwitter()
}