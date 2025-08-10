package com.naveenapps.expensemanager.core.repository

import com.naveenapps.expensemanager.core.model.Resource
import io.github.vinceglb.filekit.PlatformFile

interface BackupRepository {

    suspend fun backupData(file: PlatformFile?): Resource<Boolean>

    suspend fun restoreData(file: PlatformFile?): Resource<Boolean>
}