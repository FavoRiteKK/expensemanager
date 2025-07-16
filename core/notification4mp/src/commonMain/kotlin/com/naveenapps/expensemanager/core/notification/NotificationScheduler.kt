package com.naveenapps.expensemanager.core.notification

import com.naveenapps.expensemanager.core.repository.ReminderTimeRepository

class NotificationScheduler(
    private val reminderTimeRepository: ReminderTimeRepository,
) {

    suspend fun checkAndRestartReminder() {
        if (canWeShowNotification().not()) {
            return
        }

        setReminder()
    }

    suspend fun setReminder() {
        TODO("Not yet implemented")
    }

    fun cancelReminder() {
        TODO("Not yet implemented")
    }

    private suspend fun getTimeInMillis()/*: Calendar*/ {
        TODO("Not yet implemented")
    }

    suspend fun showNotification(
        destinationClass: String,
        title: String,
        content: String,
    ) {
        TODO("Not yet implemented")
    }

    private suspend fun canWeShowNotification(): Boolean {
        TODO("Not yet implemented")
    }

    private fun createChannelIfRequired() {
        TODO("Not yet implemented")
    }

    //    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String,
    ) {
        TODO("Not yet implemented")
    }
}

object NotificationId {
    const val DAILY_REMINDER_REQUEST_CODE = 101
}

object NotificationChannelId {
    const val CHANNEL_GENERAL = "General"
}
