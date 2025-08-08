package com.naveenapps.expensemanager.core.notification

import android.app.NotificationManager
import androidx.annotation.IdRes
import com.tweener.alarmee.channel.AlarmeeNotificationChannel
import com.tweener.alarmee.configuration.AlarmeeAndroidPlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration

actual fun createAlarmPlatformConfiguration(
    @IdRes icon: Int,
    channelId: String,
    channelName: String,
): AlarmeePlatformConfiguration {
    return AlarmeeAndroidPlatformConfiguration(
        notificationIconResId = icon,
        notificationIconColor = androidx.compose.ui.graphics.Color.Red,
        notificationChannels = listOf(
            AlarmeeNotificationChannel(
                id = channelId,
                name = channelName,
                importance = NotificationManager.IMPORTANCE_DEFAULT,
            ),
        )
    )
}