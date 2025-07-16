package com.naveenapps.expensemanager.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun databaseBuilderModule(): Module = module {
    single<RoomDatabase.Builder<ExpenseManagerDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(DATA_BASE_NAME)

        return@single Room.databaseBuilder<ExpenseManagerDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}