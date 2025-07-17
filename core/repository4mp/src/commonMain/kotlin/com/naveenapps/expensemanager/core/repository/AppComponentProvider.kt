package com.naveenapps.expensemanager.core.repository

interface AppComponentProvider {

    fun getBackupRepository(): BackupRepository

    fun getShareRepository(): ShareRepository
}