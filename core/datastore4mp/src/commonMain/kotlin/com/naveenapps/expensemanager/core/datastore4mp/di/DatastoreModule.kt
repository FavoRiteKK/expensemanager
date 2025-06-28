package com.naveenapps.expensemanager.core.datastore4mp.di

import com.naveenapps.expensemanager.core.datastore4mp.CurrencyDataStore
import com.naveenapps.expensemanager.core.datastore4mp.DateRangeDataStore
import com.naveenapps.expensemanager.core.datastore4mp.ReminderTimeDataStore
import com.naveenapps.expensemanager.core.datastore4mp.SettingsDataStore
import com.naveenapps.expensemanager.core.datastore4mp.ThemeDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal const val DATA_STORE_NAME = "expense_manager_app_data_store.preferences_pb"

internal expect fun appDataStoreModule(): Module

val dataStoreModule = appDataStoreModule() + module {
    singleOf(::ThemeDataStore)
    singleOf(::SettingsDataStore)
    singleOf(::CurrencyDataStore)
    singleOf(::ReminderTimeDataStore)
    singleOf(::DateRangeDataStore)
}
