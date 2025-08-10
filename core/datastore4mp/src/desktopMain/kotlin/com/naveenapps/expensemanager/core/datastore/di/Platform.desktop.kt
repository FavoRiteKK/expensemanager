package com.naveenapps.expensemanager.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

internal actual fun appDataStoreModule(): Module = module {
    single<DataStore<Preferences>> {
        
        /**
         *   Gets the singleton DataStore instance, creating it if necessary.
         */
        PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                val file = File(System.getProperty("java.io.tmpdir") + File.separator + "expensemanager", DATA_STORE_NAME)
                file.absolutePath.toPath()
            }
        )
    }
}