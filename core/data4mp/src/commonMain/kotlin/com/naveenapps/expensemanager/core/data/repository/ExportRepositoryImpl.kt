package com.naveenapps.expensemanager.core.data.repository

import com.naveenapps.expensemanager.core.model.ExportData
import com.naveenapps.expensemanager.core.model.Resource
import com.naveenapps.expensemanager.core.model.Transaction
import com.naveenapps.expensemanager.core.repository.ExportRepository

class ExportRepositoryImpl : ExportRepository {
    override suspend fun createCsvFile(
        uri: String?,
        transactions: List<Transaction>
    ): Resource<ExportData> {
        TODO("Not yet implemented")
    }

    override suspend fun createPdfFile(
        uri: String?,
        transactions: List<Transaction>
    ): Resource<ExportData> {
        TODO("Not yet implemented")
    }

}