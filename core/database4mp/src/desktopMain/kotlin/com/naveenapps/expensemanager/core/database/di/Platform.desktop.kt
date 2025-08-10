package com.naveenapps.expensemanager.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

internal actual fun databaseBuilderModule(): Module = module {
    single(qualifier = named("DATABASE_PATH")) {
        val dbFile = File(System.getProperty("java.io.tmpdir") + File.separator + "expensemanager", DATABASE_NAME)
        dbFile.absolutePath
    }

    single<RoomDatabase.Builder<ExpenseManagerDatabase>> {
        val dbFileAbsPath = get<String>(qualifier = named("DATABASE_PATH"))

        return@single Room.databaseBuilder<ExpenseManagerDatabase>(
            name = dbFileAbsPath,
        )
    }
}