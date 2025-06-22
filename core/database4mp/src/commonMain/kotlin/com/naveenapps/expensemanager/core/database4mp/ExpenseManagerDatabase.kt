package com.naveenapps.expensemanager.core.database4mp

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.naveenapps.expensemanager.core.database4mp.dao.AccountDao
import com.naveenapps.expensemanager.core.database4mp.dao.BudgetDao
import com.naveenapps.expensemanager.core.database4mp.dao.CategoryDao
import com.naveenapps.expensemanager.core.database4mp.dao.TransactionDao
import com.naveenapps.expensemanager.core.database4mp.entity.AccountEntity
import com.naveenapps.expensemanager.core.database4mp.entity.BudgetAccountEntity
import com.naveenapps.expensemanager.core.database4mp.entity.BudgetCategoryEntity
import com.naveenapps.expensemanager.core.database4mp.entity.BudgetEntity
import com.naveenapps.expensemanager.core.database4mp.entity.CategoryEntity
import com.naveenapps.expensemanager.core.database4mp.entity.TransactionEntity
import com.naveenapps.expensemanager.core.database4mp.utils.AccountTypeConverter
import com.naveenapps.expensemanager.core.database4mp.utils.CategoryTypeConverter
import com.naveenapps.expensemanager.core.database4mp.utils.DateConverter
import com.naveenapps.expensemanager.core.database4mp.utils.TransactionTypeConverter

/**
 * The Room database for this app
 */
@Database(
    entities = [
        CategoryEntity::class,
        TransactionEntity::class,
        AccountEntity::class,
        BudgetEntity::class,
        BudgetCategoryEntity::class,
        BudgetAccountEntity::class,
    ],
    version = 3,
    exportSchema = true,
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(
    DateConverter::class,
    TransactionTypeConverter::class,
    CategoryTypeConverter::class,
    AccountTypeConverter::class,
)
abstract class ExpenseManagerDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun transactionDao(): TransactionDao

    abstract fun accountDao(): AccountDao

    abstract fun budgetDao(): BudgetDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ExpenseManagerDatabase> {
    override fun initialize(): ExpenseManagerDatabase
}
