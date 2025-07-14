package com.naveenapps.expensemanager.feature.filter4mp.datefilter

sealed class DateFilterEvent {

    data object Saved : DateFilterEvent()
}