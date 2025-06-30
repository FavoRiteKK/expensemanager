package com.naveenapps.expensemanager.core.data4mp.repository

import com.naveenapps.expensemanager.core.model4mp.ExportData
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.model4mp.Transaction
import com.naveenapps.expensemanager.core.repository4mp.ExportRepository

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