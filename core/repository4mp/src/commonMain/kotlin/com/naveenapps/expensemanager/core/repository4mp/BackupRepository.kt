package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.Resource

interface BackupRepository {

    fun backupData(uri: String?): Resource<Boolean>

    fun restoreData(uri: String?): Resource<Boolean>
}