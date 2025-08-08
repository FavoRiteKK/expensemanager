package com.naveenapps.expensemanager.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import kotlinx.coroutines.CoroutineScope
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase> {
    return inMemoryDatabaseBuilder<ExpenseManagerDatabase>()
        .setDriver(BundledSQLiteDriver())
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
                    //in desktop, datastore instance must be unique, so as the file,
                    //hence a hash code is added
                    val name = "TEST_DATASTORE_NAME_${scope.hashCode()}.preferences_pb"
                    val file = File(
                        System.getProperty("java.io.tmpdir"),
                        name
                    )
                    file.absolutePath.toPath()
                }
            )
        }
    }
}

actual fun deleteDataStoreFile(scope: CoroutineScope) {
    val file = File(
        System.getProperty("java.io.tmpdir"),
        "TEST_DATASTORE_NAME_${scope.hashCode()}.preferences_pb"
    )
    file.delete()
}