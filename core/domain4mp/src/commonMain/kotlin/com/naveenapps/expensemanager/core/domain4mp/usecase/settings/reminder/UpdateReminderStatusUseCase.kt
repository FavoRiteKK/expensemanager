package com.naveenapps.expensemanager.core.domain4mp.usecase.settings.reminder

import com.naveenapps.expensemanager.core.repository4mp.ReminderTimeRepository

class UpdateReminderStatusUseCase(
    private val repository: ReminderTimeRepository,
) {

    suspend operator fun invoke(reminderStatus: Boolean) {
        repository.setReminderOn(reminderStatus)
    }
}
