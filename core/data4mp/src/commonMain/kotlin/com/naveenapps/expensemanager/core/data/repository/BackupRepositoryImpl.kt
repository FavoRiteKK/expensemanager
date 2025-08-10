package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.database.di.DATABASE_NAME
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BackupRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.Path.Companion.DIRECTORY_SEPARATOR

private val logger = KotlinLogging.logger {}

class BackupRepositoryImpl(
    private val fileSystem: FileSystem,
    private val dbAbsPath: String,
    private val dispatchers: AppCoroutineDispatchers,
) : BackupRepository {
    override suspend fun backupData(dir: String?): Resource<Boolean> {
        dir ?: return Resource.Error(Exception("Uri is null"))

        logger.info { "Backing up data to $dir" }

        withContext(dispatchers.io) {
            try {
                val dbFile = dir + DIRECTORY_SEPARATOR + DATABASE_NAME
                val shm = "$dbFile-shm".toPath()
                val wal = "$dbFile-wal".toPath()
                if (fileSystem.exists(dbFile.toPath())) fileSystem.delete(dbFile.toPath())
                if (fileSystem.exists(shm)) fileSystem.delete(shm)
                if (fileSystem.exists(wal)) fileSystem.delete(wal)
                fileSystem.copy(dbAbsPath.toPath(), dbFile.toPath())
                fileSystem.copy("$dbAbsPath-shm".toPath(), shm)
                fileSystem.copy("$dbAbsPath-wal".toPath(), wal)
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }

    override suspend fun restoreData(dir: String?): Resource<Boolean> {
        dir ?: return Resource.Error(Exception("Uri is null"))

        logger.info { "Restoring data from $dir" }

        withContext(dispatchers.io) {
            try {
                val dbFile = dir + DIRECTORY_SEPARATOR + DATABASE_NAME
                val shm = "$dbFile-shm".toPath()
                val wal = "$dbFile-wal".toPath()
                logger.info { "dbFile $dbFile" }
                logger.info { "shm $shm" }
                logger.info { "wal $wal" }
                logger.info { "dbAbsPath $dbAbsPath" }
                if (fileSystem.exists(dbAbsPath.toPath())) fileSystem.delete(dbAbsPath.toPath())
                if (fileSystem.exists("$dbAbsPath-shm".toPath())) fileSystem.delete("$dbAbsPath-shm".toPath())
                if (fileSystem.exists("$dbAbsPath-wal".toPath())) fileSystem.delete("$dbAbsPath-wal".toPath())
                fileSystem.copy(dbFile.toPath(), dbAbsPath.toPath())
                fileSystem.copy(shm, "$dbAbsPath-shm".toPath())
                fileSystem.copy(wal, "$dbAbsPath-wal".toPath())
            } catch (e: Exception) {
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }
}