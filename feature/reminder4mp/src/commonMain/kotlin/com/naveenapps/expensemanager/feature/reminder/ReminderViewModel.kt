package com.naveenapps.expensemanager.feature.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naveenapps.expensemanager.core.common.utils.fromTimeAndHour
import com.naveenapps.expensemanager.core.common.utils.toTimeAndMinutesWithAMPM
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.GetReminderStatusUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.GetReminderTimeUseCase
import com.naveenapps.expensemanager.core.domain.usecase.settings.reminder.UpdateReminderStatusUseCase
import com.naveenapps.expensemanager.core.navigation.AppComposeNavigator
import com.naveenapps.expensemanager.core.notification.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ReminderViewModel(
    getReminderTimeUseCase: GetReminderTimeUseCase,
    getReminderStatusUseCase: GetReminderStatusUseCase,
    private val updateReminderStatusUseCase: UpdateReminderStatusUseCase,
    private val notificationScheduler: NotificationScheduler,
    private val appComposeNavigator: AppComposeNavigator,
) : ViewModel() {

    private val _reminderOn = MutableStateFlow(false)
    val reminderOn = _reminderOn.asStateFlow()

    private val _reminderTime = MutableStateFlow("06:00")
    val reminderTime = _reminderTime.asStateFlow()

    private val _state =
        MutableStateFlow(ReminderState(shouldShowRationale = false, notificationAllowed = true))
    val state = _state.asStateFlow()

    init {
        getReminderStatusUseCase.invoke().onEach {
            _reminderOn.value = it
        }.launchIn(viewModelScope)

        getReminderTimeUseCase.invoke().onEach {
            _reminderTime.value =
                fromTimeAndHour(it.hour, it.minute).toTimeAndMinutesWithAMPM()
        }.launchIn(viewModelScope)
    }

    fun saveReminderStatus(status: Boolean, title: String, content: String) {
        viewModelScope.launch {
            updateReminderStatusUseCase.invoke(status)

            if (status) {
                notificationScheduler.setReminder(title, content)
            } else {
                notificationScheduler.cancelReminder()
            }
        }
    }

    fun launchPermissionRequest() {
        println("Not yet implemented")
    }

    fun closePage() {
        appComposeNavigator.popBackStack()
    }
}
