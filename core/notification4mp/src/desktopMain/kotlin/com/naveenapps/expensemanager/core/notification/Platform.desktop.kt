package com.naveenapps.expensemanager.core.notification

import com.tweener.alarmee.configuration.AlarmeePlatformConfiguration
import com.tweener.alarmee.configuration.AlarmeePlatformConfigurationNonMobile

actual fun createAlarmPlatformConfiguration(
    icon: Int,
    channelId: String,
    channelName: String
): AlarmeePlatformConfiguration {
    return AlarmeePlatformConfigurationNonMobile()
}