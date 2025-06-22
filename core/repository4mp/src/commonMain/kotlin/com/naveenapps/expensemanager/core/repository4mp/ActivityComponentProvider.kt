package com.naveenapps.expensemanager.core.repository4mp

interface ActivityComponentProvider {

    fun getBackupRepository(): BackupRepository

    fun getShareRepository(): ShareRepository
}