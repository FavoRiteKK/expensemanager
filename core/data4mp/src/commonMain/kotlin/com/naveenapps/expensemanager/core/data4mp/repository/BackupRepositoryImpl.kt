package com.naveenapps.expensemanager.core.data4mp.repository

import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.repository4mp.BackupRepository

class BackupRepositoryImpl : BackupRepository {
    override fun backupData(uri: String?): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override fun restoreData(uri: String?): Resource<Boolean> {
        TODO("Not yet implemented")
    }
}