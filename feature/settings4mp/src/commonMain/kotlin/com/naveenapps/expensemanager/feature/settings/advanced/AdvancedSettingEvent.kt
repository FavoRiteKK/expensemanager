package com.naveenapps.expensemanager.feature.settings.advanced

sealed class AdvancedSettingEvent {

    data object BackupSuccess : AdvancedSettingEvent()
    data object BackupFail : AdvancedSettingEvent()

    data object RestoreSuccess : AdvancedSettingEvent()
    data object RestoreFail : AdvancedSettingEvent()
}