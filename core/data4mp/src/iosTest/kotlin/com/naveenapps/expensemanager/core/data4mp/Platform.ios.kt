package com.naveenapps.expensemanager.core.data4mp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase> {
    return inMemoryDatabaseBuilder<ExpenseManagerDatabase>()
        .setDriver(BundledSQLiteDriver())
}

@OptIn(ExperimentalForeignApi::class)
actual fun testDataStoreModule(scope: CoroutineScope): Module {
    return module {
        single<DataStore<Preferences>> {

            /**
             *   Gets the singleton DataStore instance, creating it if necessary.
             */
            PreferenceDataStoreFactory.createWithPath(
                scope = scope,
                produceFile = {
                    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                    (requireNotNull(documentDirectory).path + "TEST_DATASTORE_NAME.preferences_pb").toPath()
                }
            )
        }
    }
}

actual fun deleteDataStoreFile(scope: CoroutineScope) {
    /* no-op */
}