package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BackupRepository

class BackupRepositoryImpl : BackupRepository {
    override fun backupData(uri: String?): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override fun restoreData(uri: String?): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}