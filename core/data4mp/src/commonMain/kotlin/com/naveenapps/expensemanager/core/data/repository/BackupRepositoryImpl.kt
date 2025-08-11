package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.database.di.DATABASE_NAME
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BackupRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.absolutePath
import io.github.vinceglb.filekit.copyTo
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.parent
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.readString
import io.github.vinceglb.filekit.resolve
import io.github.vinceglb.filekit.utils.toPath
import io.github.vinceglb.filekit.writeString
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.DIRECTORY_SEPARATOR
import okio.Path.Companion.toPath

private val logger = KotlinLogging.logger {}

class BackupRepositoryImpl(
    private val fileSystem: FileSystem,
    private val dbAbsPath: String,
    private val dispatchers: AppCoroutineDispatchers,
) : BackupRepository {
    override suspend fun backupData(file: PlatformFile?): Resource<Boolean> {
        file ?: return Resource.Error(Exception("Uri is null"))

        logger.info { "Backing up data to ${file.absolutePath()}" }

        withContext(dispatchers.io) {
            try {
//                val dbFile = dir + DIRECTORY_SEPARATOR + DATABASE_NAME
//                val shm = "$file.path-shm".toPath()
//                val wal = "$dbFile-wal".toPath()
//                if (fileSystem.exists(dbFile.toPath())) fileSystem.delete(dbFile.toPath())
//                if (fileSystem.exists(shm)) fileSystem.delete(shm)
//                if (fileSystem.exists(wal)) fileSystem.delete(wal)
//                fileSystem.copy(dbAbsPath.toPath(), dbFile.toPath())
//                fileSystem.copy("$dbAbsPath-shm".toPath(), shm)
//                fileSystem.copy("$dbAbsPath-wal".toPath(), wal)

//                val content = mergeContentOf(dbAbsPath)
//                file.writeString(content)

//                val destPath = file.parent()
                PlatformFile(path = dbAbsPath.toPath())
                    .copyTo(file)
//                PlatformFile(path = "$dbAbsPath-shm".toPath())
//                    .copyTo(PlatformFile(path = "$destPath-shm"))
//                PlatformFile(path = "$dbAbsPath-wal".toPath())
//                    .copyTo(PlatformFile(path = "$destPath-wal"))

            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }

    private suspend fun mergeContentOf(dbAbsPath: String): String {
        val db = PlatformFile(path = dbAbsPath).readString()
//        val shm = PlatformFile(path = "$dbAbsPath-shm").readString()
//        val wal = PlatformFile(path = "$dbAbsPath-wal").readString()
        return db
    }

    override suspend fun restoreData(file: PlatformFile?): Resource<Boolean> {
        file ?: return Resource.Error(Exception("Uri is null"))

        logger.info { "Restoring data from ${file.absolutePath()}" }

        withContext(dispatchers.io) {
            try {
//                val dbFile = dir + DIRECTORY_SEPARATOR + DATABASE_NAME
//                val shm = "$dbFile-shm".toPath()
//                val wal = "$dbFile-wal".toPath()
//                fileSystem.copy(dbFile.toPath(), dbAbsPath.toPath())
//                fileSystem.copy(shm, "$dbAbsPath-shm".toPath())
//                fileSystem.copy(wal, "$dbAbsPath-wal".toPath())
//                val db = file.readString()
                file.copyTo(PlatformFile(path = dbAbsPath.toPath()))
//                PlatformFile(path = "$dbAbsPath-shm".toPath()).writeString(shm)
//                PlatformFile(path = "$dbAbsPath-wal".toPath()).writeString(wal)

            } catch (e: Exception) {
                println("error: $e")
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }
}