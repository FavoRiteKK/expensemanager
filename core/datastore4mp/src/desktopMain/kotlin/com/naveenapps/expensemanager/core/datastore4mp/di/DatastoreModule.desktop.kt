package com.naveenapps.expensemanager.core.datastore4mp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

internal actual fun appDataStoreModule(): Module = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { dataStoreFileName ->
                val file = File(System.getProperty("java.io.tmpdir"), dataStoreFileName)
                file.absolutePath
            }
        )
    }
}