package com.naveenapps.expensemanager.feature.about

sealed class AboutAction {

    data object ClosePage : AboutAction()

    data object Mail : AboutAction()

    data object OpenTerms : AboutAction()

    data object OpenPrivacy : AboutAction()

    data object OpenLicense : AboutAction()
}