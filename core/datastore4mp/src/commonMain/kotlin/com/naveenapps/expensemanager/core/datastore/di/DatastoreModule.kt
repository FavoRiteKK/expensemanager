package com.naveenapps.expensemanager.core.datastore.di

import com.naveenapps.expensemanager.core.datastore.CurrencyDataStore
import com.naveenapps.expensemanager.core.datastore.DateRangeDataStore
import com.naveenapps.expensemanager.core.datastore.ReminderTimeDataStore
import com.naveenapps.expensemanager.core.datastore.SettingsDataStore
import com.naveenapps.expensemanager.core.datastore.ThemeDataStore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal const val DATA_STORE_NAME = "expense_manager_app_data_store.preferences_pb"

internal expect fun appDataStoreModule(): Module

val dataStore = appDataStoreModule() + module {
    singleOf(::ThemeDataStore)
    singleOf(::SettingsDataStore)
    singleOf(::CurrencyDataStore)
    singleOf(::ReminderTimeDataStore)
    singleOf(::DateRangeDataStore)
}
