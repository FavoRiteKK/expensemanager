package com.naveenapps.expensemanager.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal actual fun databaseBuilderModule(): Module = module {
    single(qualifier = named("DATABASE_PATH")) {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        dbFile.absolutePath
    }

    single<RoomDatabase.Builder<ExpenseManagerDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFileAbsPath = get<String>(qualifier = named("DATABASE_PATH"))

        return@single Room.databaseBuilder<ExpenseManagerDatabase>(
            context = appContext,
            name = dbFileAbsPath
        )
    }
}