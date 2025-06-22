package com.naveenapps.expensemanager.core.database4mp.utils

import androidx.room.TypeConverter
import com.naveenapps.expensemanager.core.model4mp.CategoryType

object CategoryTypeConverter {

    @TypeConverter
    fun ordinalToCategoryType(value: Int?): CategoryType? {
        return value?.let { CategoryType.entries[value] }
    }

    @TypeConverter
    fun categoryTypeToOrdinal(categoryType: CategoryType?): Int? {
        return categoryType?.ordinal
    }
}
