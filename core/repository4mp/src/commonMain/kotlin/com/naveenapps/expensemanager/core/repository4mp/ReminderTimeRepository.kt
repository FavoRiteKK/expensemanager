package com.naveenapps.expensemanager.core.repository4mp

import com.naveenapps.expensemanager.core.model4mp.ReminderTimeState
import com.naveenapps.expensemanager.core.model4mp.Resource
import kotlinx.coroutines.flow.Flow

interface ReminderTimeRepository {

    suspend fun saveReminderTime(reminderTime: ReminderTimeState): Boolean

    fun getReminderTime(): Flow<ReminderTimeState>

    fun isReminderOn(): Flow<Boolean>

    suspend fun setReminderOn(reminder: Boolean): Resource<Boolean>
}
