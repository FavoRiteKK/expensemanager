package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.repository.BackupRepository
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.copyTo
import io.github.vinceglb.filekit.utils.toPath
import kotlinx.coroutines.withContext

class BackupRepositoryImpl(
    private val dbAbsPath: String,
    private val dispatchers: AppCoroutineDispatchers,
) : BackupRepository {
    override suspend fun backupData(file: PlatformFile?): Resource<Boolean> {
        file ?: return Resource.Error(Exception("Uri is null"))

        withContext(dispatchers.io) {
            try {
                PlatformFile(path = dbAbsPath.toPath()).copyTo(file)

            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }

    override suspend fun restoreData(file: PlatformFile?): Resource<Boolean> {
        file ?: return Resource.Error(Exception("Uri is null"))

        withContext(dispatchers.io) {
            try {
                file.copyTo(PlatformFile(path = dbAbsPath.toPath()))

            } catch (e: Exception) {
                println("error: $e")
                return@withContext Resource.Error(e)
            }
        }
        return Resource.Success(true)
    }
}