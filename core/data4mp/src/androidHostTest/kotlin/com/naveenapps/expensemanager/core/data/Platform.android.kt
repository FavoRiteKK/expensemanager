package com.naveenapps.expensemanager.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase> {
    val context =
        InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext
    return inMemoryDatabaseBuilder<ExpenseManagerDatabase>(context)
        .setDriver(AndroidSQLiteDriver())
}

actual fun testDataStoreModule(scope: CoroutineScope): Module {
    return module {
        single<DataStore<Preferences>> {

            /**
             *   Gets the singleton DataStore instance, creating it if necessary.
             */
            PreferenceDataStoreFactory.createWithPath(
                scope = scope,
                produceFile = {
                    ApplicationProvider.getApplicationContext<Context>().filesDir.resolve(
                        "TEST_DATASTORE_NAME.preferences_pb"
                    ).absolutePath.toPath()
                }
            )
        }
    }
}

actual fun deleteDataStoreFile(scope: CoroutineScope) {
    /* no-op */
}