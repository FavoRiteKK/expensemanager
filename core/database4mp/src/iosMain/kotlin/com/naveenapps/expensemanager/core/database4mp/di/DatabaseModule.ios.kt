package com.naveenapps.expensemanager.core.database4mp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal actual fun databaseBuilderModule(): Module = module {
    single<RoomDatabase.Builder<ExpenseManagerDatabase>> {
        val dbFilePath = documentDirectory() + "/$DATA_BASE_NAME"
        return@single Room.databaseBuilder<ExpenseManagerDatabase>(
            name = dbFilePath,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
