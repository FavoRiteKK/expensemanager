package com.naveenapps.expensemanager.core.data.di

import com.naveenapps.expensemanager.core.data.repository.SettingsRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.ThemeRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.VersionCheckerRepositoryImpl
import com.naveenapps.expensemanager.core.repository.SettingsRepository
import com.naveenapps.expensemanager.core.repository.ThemeRepository
import com.naveenapps.expensemanager.core.repository.VersionCheckerRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::VersionCheckerRepositoryImpl) bind VersionCheckerRepository::class
    singleOf(::ThemeRepositoryImpl) bind ThemeRepository::class
    singleOf(::SettingsRepositoryImpl) bind SettingsRepository::class
}