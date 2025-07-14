package com.naveenapps.expensemanager.feature.filter4mp.datefilter

import kotlinx.datetime.LocalDateTime

sealed class DateFilterAction {

    data object Save : DateFilterAction()

    data object ShowFromDateSelection : DateFilterAction()

    data object ShowToDateSelection : DateFilterAction()

    data class SaveFromDate(val date: LocalDateTime) : DateFilterAction()

    data class SaveToDate(val date: LocalDateTime) : DateFilterAction()

    data object DismissDateSelection : DateFilterAction()
}