package com.naveenapps.expensemanager.core.notification

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

expect fun createAlarmPlatformConfiguration(
    icon: Int = -1,
    channelId: String = NotificationChannelId.CHANNEL_GENERAL,
    channelName: String = NotificationChannelId.CHANNEL_GENERAL,
): AlarmeePlatformConfiguration


object NotificationId {
    const val DAILY_REMINDER_REQUEST_CODE = 101
    const val UUID = "logReminderId"
}

object NotificationChannelId {
    const val CHANNEL_GENERAL = "General"
}
