package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.ExportData
import com.naveenapps.expensemanager.core.model4mp.Resource
import com.naveenapps.expensemanager.core.model4mp.Transaction

interface ExportRepository {

    suspend fun createCsvFile(uri: String?, transactions: List<Transaction>): Resource<ExportData>

    suspend fun createPdfFile(uri: String?, transactions: List<Transaction>): Resource<ExportData>
}
