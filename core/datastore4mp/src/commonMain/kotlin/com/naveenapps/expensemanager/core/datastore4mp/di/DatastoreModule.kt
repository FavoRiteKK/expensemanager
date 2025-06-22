package com.naveenapps.expensemanager.core.datastore4mp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.naveenapps.expensemanager.core.datastore4mp.CurrencyDataStore
import com.naveenapps.expensemanager.core.datastore4mp.DateRangeDataStore
import com.naveenapps.expensemanager.core.datastore4mp.ReminderTimeDataStore
import com.naveenapps.expensemanager.core.datastore4mp.SettingsDataStore
import com.naveenapps.expensemanager.core.datastore4mp.ThemeDataStore
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val DATA_STORE_NAME = "expense_manager_app_data_store"

/**
 *   Gets the singleton DataStore instance, creating it if necessary.
 */
internal fun createDataStore(producePath: (String) -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath(DATA_STORE_NAME).toPath() }
    )

internal expect fun appDataStoreModule(): Module

val dataStoreModule = appDataStoreModule() + module {
    singleOf(::ThemeDataStore)
    singleOf(::SettingsDataStore)
    singleOf(::CurrencyDataStore)
    singleOf(::ReminderTimeDataStore)
    singleOf(::DateRangeDataStore)
}
