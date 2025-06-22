package com.naveenapps.expensemanager.core.database4mp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

internal actual fun databaseBuilderModule(): Module = module {
    single<RoomDatabase.Builder<ExpenseManagerDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DATA_BASE_NAME)
        return@single Room.databaseBuilder<ExpenseManagerDatabase>(
            name = dbFile.absolutePath,
        )
    }
}