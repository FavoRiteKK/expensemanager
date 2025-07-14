package com.naveenapps.expensemanager.feature.filter4mp.type

sealed class FilterTypeEvent {

    data object Saved : FilterTypeEvent()
}