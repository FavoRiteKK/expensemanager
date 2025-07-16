package com.naveenapps.expensemanager.core.data

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database.ExpenseManagerDatabase
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module

expect fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase>

internal expect fun testDataStoreModule(scope: CoroutineScope): Module

internal expect fun deleteDataStoreFile(scope: CoroutineScope)
