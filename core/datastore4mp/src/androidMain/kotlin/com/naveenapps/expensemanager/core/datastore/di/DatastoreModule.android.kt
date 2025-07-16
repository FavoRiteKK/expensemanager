package com.naveenapps.expensemanager.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun appDataStoreModule(): Module = module {

    single<DataStore<Preferences>> {

        /**
         *   Gets the singleton DataStore instance, creating it if necessary.
         */
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { androidContext().filesDir.resolve(DATA_STORE_NAME).absolutePath.toPath() }
        )
    }
}