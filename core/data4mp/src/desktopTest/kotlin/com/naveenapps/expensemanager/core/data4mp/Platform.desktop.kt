package com.naveenapps.expensemanager.core.data4mp

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase

actual fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase> {
    return inMemoryDatabaseBuilder<ExpenseManagerDatabase>()
        .setDriver(BundledSQLiteDriver())
}