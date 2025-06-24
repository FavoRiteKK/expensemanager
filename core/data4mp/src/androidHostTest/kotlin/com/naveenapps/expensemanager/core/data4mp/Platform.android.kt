package com.naveenapps.expensemanager.core.data4mp

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.platform.app.InstrumentationRegistry
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase

actual fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase> {
    val context =
        InstrumentationRegistry.getInstrumentation().targetContext
            .applicationContext
    return inMemoryDatabaseBuilder<ExpenseManagerDatabase>(context)
        .setDriver(AndroidSQLiteDriver())
}