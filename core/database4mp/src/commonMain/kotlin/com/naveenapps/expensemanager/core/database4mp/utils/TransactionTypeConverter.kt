package com.naveenapps.expensemanager.core.database4mp.utils

import androidx.room.TypeConverter
import com.naveenapps.expensemanager.core.model4mp.TransactionType

object TransactionTypeConverter {

    @TypeConverter
    fun ordinalToTransactionType(value: Int?): TransactionType? {
        return value?.let { TransactionType.entries[it] }
    }

    @TypeConverter
    fun transactionTypeToOrdinal(transactionType: TransactionType?): Int? {
        return transactionType?.ordinal
    }
}
