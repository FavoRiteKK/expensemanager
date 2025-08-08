package com.naveenapps.expensemanager.feature.reminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import expensemanager.feature.reminder4mp.generated.resources.Res
import expensemanager.feature.reminder4mp.generated.resources.notification_description
import expensemanager.feature.reminder4mp.generated.resources.notification_permission_message
import expensemanager.feature.reminder4mp.generated.resources.notification_time
import expensemanager.feature.reminder4mp.generated.resources.notification_title
import expensemanager.feature.reminder4mp.generated.resources.permission_disabled
import expensemanager.feature.reminder4mp.generated.resources.reminder
import expensemanager.feature.reminder4mp.generated.resources.reminder_notification
import expensemanager.feature.reminder4mp.generated.resources.reminder_notification_message
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel = koinViewModel(),
) {
    val title = stringResource(Res.string.notification_title)
    val content = stringResource(Res.string.notification_description)

    var showTimePicker by remember { mutableStateOf(false) }
    if (showTimePicker) {
        ReminderTimePickerView {
            showTimePicker = false
            if (it) {
                viewModel.saveReminderStatus(
                    true,
                    title = title,
                    content = content
                )
            }
        }
    }

    val reminderStatus by viewModel.reminderOn.collectAsState()
    val reminderTime by viewModel.reminderTime.collectAsState()
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = {
        TopNavigationBar(
            onClick = {
                viewModel.closePage()
            },
            title = stringResource(Res.string.reminder),
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
        ) {
            if (state.notificationAllowed) {
                SwitchSettingsItem(
                    modifier = Modifier
                        .clickable {
                            viewModel.saveReminderStatus(reminderStatus.not(), title, content)
                        }
                        .padding(top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    icon = Icons.Default.NotificationsActive,
                    title = stringResource(resource = Res.string.reminder_notification),
                    description = stringResource(resource = Res.string.reminder_notification_message),
                    checked = reminderStatus,
                    checkChange = {
                        viewModel.saveReminderStatus(it, title, content)
                    },
                )
                SettingsItem(
                    modifier = Modifier
                        .clickable {
                            if (reminderStatus) {
                                showTimePicker = true
                            }
                        }
                        .padding(top = 8.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    title = stringResource(resource = Res.string.notification_time),
                    description = reminderTime,
                    icon = Icons.Default.AccessTime,
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    val shouldShowRationale = state.shouldShowRationale
                    val textToShow = if (shouldShowRationale) {
                        stringResource(Res.string.notification_permission_message)
                    } else {
                        stringResource(Res.string.permission_disabled)
                    }

                    Text(textToShow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            viewModel.launchPermissionRequest()
                        },
                    ) {
                        Text("Request permission")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsItem(
    title: String,
    description: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            imageVector = icon,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
        ) {
            Text(text = title)
            Text(text = description, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun SwitchSettingsItem(
    title: String,
    description: String,
    checked: Boolean,
    icon: ImageVector,
    checkChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            imageVector = icon,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .align(Alignment.CenterVertically),
        ) {
            Text(text = title)
            Text(text = description, style = MaterialTheme.typography.labelMedium)
        }
        Switch(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically),
            checked = checked,
            onCheckedChange = checkChange,
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun ReminderScreenPreview() {
    ExpenseManagerTheme {
        ReminderScreen()
    }
}
