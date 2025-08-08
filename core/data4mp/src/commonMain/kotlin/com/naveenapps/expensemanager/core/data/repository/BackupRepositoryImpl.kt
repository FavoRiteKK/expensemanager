package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BackupRepository
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath

class BackupRepositoryImpl(
    private val fileSystem: FileSystem,
    private val dbAbsPath: String,
    private val dispatchers: AppCoroutineDispatchers,
) : BackupRepository {
    override suspend fun backupData(uri: String?): Resource<Boolean> {
        uri ?: return Resource.Error(Exception("Uri is null"))

        withContext(dispatchers.io) {
            val dest = uri.toPath()
            val shm = "$uri-shm".toPath()
            val wal = "$uri-wal".toPath()
            if (fileSystem.exists(dest)) fileSystem.delete(dest)
            if (fileSystem.exists(shm)) fileSystem.delete(shm)
            if (fileSystem.exists(wal)) fileSystem.delete(wal)
            fileSystem.copy(dbAbsPath.toPath(), dest)
            fileSystem.copy("$dbAbsPath-shm".toPath(), shm)
            fileSystem.copy("$dbAbsPath-wal".toPath(), wal)
        }
        return Resource.Success(true)
    }

    override suspend fun restoreData(uri: String?): Resource<Boolean> {
        uri ?: return Resource.Error(Exception("Uri is null"))

        withContext(dispatchers.io) {
            val target = uri.toPath()
            val shm = "$uri-shm".toPath()
            val wal = "$uri-wal".toPath()
            fileSystem.copy(target, dbAbsPath.toPath())
            fileSystem.copy(shm, "$dbAbsPath-shm".toPath())
            fileSystem.copy(wal, "$dbAbsPath-wal".toPath())
        }
        return Resource.Success(true)
    }
}