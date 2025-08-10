package com.naveenapps.expensemanager.core.repository

import com.naveenapps.expensemanager.core.model.Resource

interface BackupRepository {

    suspend fun backupData(dir: String?): Resource<Boolean>

    suspend fun restoreData(dir: String?): Resource<Boolean>
}