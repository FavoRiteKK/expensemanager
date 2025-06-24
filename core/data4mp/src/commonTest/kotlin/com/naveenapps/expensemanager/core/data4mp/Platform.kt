package com.naveenapps.expensemanager.core.data4mp

import androidx.room.Room
import androidx.room.RoomDatabase
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase

expect fun Room.lwInMemoryDatabaseBuilder(): RoomDatabase.Builder<ExpenseManagerDatabase>