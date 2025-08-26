package com.naveenapps.expensemanager.core.notification

import com.naveenapps.expensemanager.core.common.utils.asCurrentDateTime
import com.naveenapps.expensemanager.core.repository.PlatformRepository
import com.naveenapps.expensemanager.core.repository.ReminderTimeRepository
import com.tweener.alarmee.createAlarmeeService
import com.tweener.alarmee.model.Alarmee
import com.tweener.alarmee.model.AndroidNotificationConfiguration
import com.tweener.alarmee.model.AndroidNotificationPriority
import com.tweener.alarmee.model.IosNotificationConfiguration
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class NotificationScheduler(
    private val reminderTimeRepository: ReminderTimeRepository,
    private val platformRepository: PlatformRepository,
) {
    private val localService by lazy {
        val alarmeeService = createAlarmeeService()
        alarmeeService.initialize(
            platformConfiguration = createAlarmPlatformConfiguration(
                icon = platformRepository.icLogReminderIdRes,
            )
        )
        alarmeeService.local
    }

    suspend fun setReminder(title: String, content: String) {
        cancelReminder()

        localService.schedule(
            alarmee = Alarmee(
                uuid = NotificationId.UUID,
                notificationTitle = title,
                notificationBody = content,
                scheduledDateTime = getReminderTime(),
                androidNotificationConfiguration = AndroidNotificationConfiguration(
                    priority = AndroidNotificationPriority.DEFAULT,
                    channelId = NotificationChannelId.CHANNEL_GENERAL,
                ),
                iosNotificationConfiguration = IosNotificationConfiguration(),
            )
        )
    }

    fun cancelReminder() {
        localService.cancel(NotificationId.UUID)
    }

    private suspend fun getReminderTime(): LocalDateTime {
        val reminderTimeState = reminderTimeRepository.getReminderTime().first()
        val now = Clock.System.now().asCurrentDateTime()
        return LocalDateTime(
            date = now.date,
            time = LocalTime(hour = reminderTimeState.hour, minute = reminderTimeState.minute)
        )
    }
}
