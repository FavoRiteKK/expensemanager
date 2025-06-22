package com.naveenapps.expensemanager.core.database4mp.di

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import com.naveenapps.expensemanager.core.common4mp.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.database4mp.ExpenseManagerDatabase
import com.naveenapps.expensemanager.core.database4mp.dao.AccountDao
import com.naveenapps.expensemanager.core.database4mp.dao.BudgetDao
import com.naveenapps.expensemanager.core.database4mp.dao.CategoryDao
import com.naveenapps.expensemanager.core.database4mp.dao.TransactionDao
import org.koin.core.module.Module
import org.koin.dsl.module

internal const val DATA_BASE_NAME = "expense_manager_database.db"

internal expect fun databaseBuilderModule(): Module

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("ALTER TABLE account ADD COLUMN sequence INTEGER NOT NULL DEFAULT ${Int.MAX_VALUE}")
    }
}

val databaseModule = databaseBuilderModule() + module {
    single<ExpenseManagerDatabase> {
        val appDispatchers: AppCoroutineDispatchers = get()

        get<RoomDatabase.Builder<ExpenseManagerDatabase>>()
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigrationOnDowngrade(true)
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(appDispatchers.io)
            .build()
    }

    single<CategoryDao> { get<ExpenseManagerDatabase>().categoryDao() }
    single<AccountDao> { get<ExpenseManagerDatabase>().accountDao() }
    single<TransactionDao> { get<ExpenseManagerDatabase>().transactionDao() }
    single<BudgetDao> { get<ExpenseManagerDatabase>().budgetDao() }
}
