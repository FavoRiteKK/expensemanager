package com.naveenapps.expensemanager.core.repository

import com.naveenapps.expensemanager.core.model.Resource

interface BackupRepository {

    suspend fun backupData(uri: String?): Resource<Boolean>

    suspend fun restoreData(uri: String?): Resource<Boolean>
}