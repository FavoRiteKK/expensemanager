package com.naveenapps.expensemanager.core.datastore4mp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun appDataStoreModule(): Module = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { dataStoreFileName ->
                androidContext().filesDir.resolve(dataStoreFileName).absolutePath
            }
        )
    }
}