package com.naveenapps.expensemanager.core.data.di

import com.naveenapps.expensemanager.core.data.repository.AccountRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.BudgetRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.CategoryRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.CountryRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.CurrencyRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.DateRangeFilterRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.ExportRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.FirebaseSettingsRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.JsonConverterRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.ReminderTimeRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.SettingsRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.ThemeRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.TransactionRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.VersionCheckerRepositoryImpl
import com.naveenapps.expensemanager.core.repository.AccountRepository
import com.naveenapps.expensemanager.core.repository.BudgetRepository
import com.naveenapps.expensemanager.core.repository.CategoryRepository
import com.naveenapps.expensemanager.core.repository.CountryRepository
import com.naveenapps.expensemanager.core.repository.CurrencyRepository
import com.naveenapps.expensemanager.core.repository.DateRangeFilterRepository
import com.naveenapps.expensemanager.core.repository.ExportRepository
import com.naveenapps.expensemanager.core.repository.FirebaseSettingsRepository
import com.naveenapps.expensemanager.core.repository.JsonConverterRepository
import com.naveenapps.expensemanager.core.repository.ReminderTimeRepository
import com.naveenapps.expensemanager.core.repository.SettingsRepository
import com.naveenapps.expensemanager.core.repository.ThemeRepository
import com.naveenapps.expensemanager.core.repository.TransactionRepository
import com.naveenapps.expensemanager.core.repository.VersionCheckerRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


internal expect fun platformRepository(): Module

expect interface LWPermission
expect interface LWPermissionsController {
    suspend fun providePermission(permission: LWPermission)
}
expect object LWWriteStoragePermission : LWPermission
expect class LWDeniedAlwaysException : Exception
expect class LWDeniedException : Exception

val repository = platformRepository() + module {
    singleOf(::AccountRepositoryImpl) bind AccountRepository::class
    singleOf(::BudgetRepositoryImpl) bind BudgetRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    singleOf(::CountryRepositoryImpl) bind CountryRepository::class
    singleOf(::CurrencyRepositoryImpl) bind CurrencyRepository::class
    singleOf(::DateRangeFilterRepositoryImpl) bind DateRangeFilterRepository::class
    singleOf(::ExportRepositoryImpl) bind ExportRepository::class
    singleOf(::FirebaseSettingsRepositoryImpl) bind FirebaseSettingsRepository::class
    singleOf(::JsonConverterRepositoryImpl) bind JsonConverterRepository::class
    singleOf(::ReminderTimeRepositoryImpl) bind ReminderTimeRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    singleOf(::TransactionRepositoryImpl) bind TransactionRepository::class
    singleOf(::VersionCheckerRepositoryImpl) bind VersionCheckerRepository::class
}