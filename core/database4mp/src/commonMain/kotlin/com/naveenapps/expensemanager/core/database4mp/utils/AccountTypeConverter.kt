package com.naveenapps.expensemanager.core.database4mp.utils

import androidx.room.TypeConverter
import com.naveenapps.expensemanager.core.model4mp.AccountType

object AccountTypeConverter {

    @TypeConverter
    fun ordinalToAccountType(value: Int?): AccountType? {
        return value?.let { AccountType.entries[value] }
    }

    @TypeConverter
    fun accountTypeToOrdinal(accountType: AccountType?): Int? {
        return accountType?.ordinal
    }
}
