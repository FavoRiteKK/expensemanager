package com.naveenapps.expensemanager.core.data.di

import com.naveenapps.expensemanager.core.data.repository.BackupRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.FirebaseSettingsRepositoryImpl
import com.naveenapps.expensemanager.core.data.repository.ShareRepositoryImpl
import com.naveenapps.expensemanager.core.repository.AppComponentProvider
import com.naveenapps.expensemanager.core.repository.BackupRepository
import com.naveenapps.expensemanager.core.repository.FirebaseSettingsRepository
import com.naveenapps.expensemanager.core.repository.ShareRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val component = module {
    factory<BackupRepository> {
        BackupRepositoryImpl(
            fileSystem = get(),
            dbAbsPath = get(qualifier = named("DATABASE_PATH")),
            dispatchers = get(),
        )
    }
    factory<FirebaseSettingsRepository> { FirebaseSettingsRepositoryImpl() }
    factory<ShareRepository> { ShareRepositoryImpl(get(), get()) }
    factory<AppComponentProvider> {
        object : AppComponentProvider {
            override fun getBackupRepository(): BackupRepository {
                return get<BackupRepository>()
            }

            override fun getShareRepository(): ShareRepository {
                return get<ShareRepository>()
            }
        }
    }
}