package com.naveenapps.expensemanager.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNotifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.RateReview
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.designsystem.utils.ObserveAsEvents
import com.naveenapps.expensemanager.core.model.Currency
import com.naveenapps.expensemanager.core.model.Theme
import com.naveenapps.expensemanager.core.repository.ShareRepository
import com.naveenapps.expensemanager.feature.theme.ThemeDialogView
import expensemanager.feature.about4mp.generated.resources.about_us
import expensemanager.feature.settings4mp.generated.resources.Res
import expensemanager.feature.settings4mp.generated.resources.about_the_app_information
import expensemanager.feature.settings4mp.generated.resources.advanced
import expensemanager.feature.settings4mp.generated.resources.advanced_config_message
import expensemanager.feature.settings4mp.generated.resources.currency
import expensemanager.feature.settings4mp.generated.resources.export
import expensemanager.feature.settings4mp.generated.resources.export_message
import expensemanager.feature.settings4mp.generated.resources.rate_us
import expensemanager.feature.settings4mp.generated.resources.rate_us_message
import expensemanager.feature.settings4mp.generated.resources.recommend_to_friend
import expensemanager.feature.settings4mp.generated.resources.reminder_notification
import expensemanager.feature.settings4mp.generated.resources.selected_daily_reminder_time
import expensemanager.feature.settings4mp.generated.resources.settings
import expensemanager.feature.settings4mp.generated.resources.share
import expensemanager.feature.settings4mp.generated.resources.system_default
import expensemanager.feature.settings4mp.generated.resources.theme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    shareRepository: ShareRepository,
    viewModel: SettingsViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    ObserveAsEvents(viewModel.event) {
        when (it) {
            SettingEvent.RateUs -> {
                shareRepository.openRateUs()
            }

            SettingEvent.ShareApp -> {
                shareRepository.share()
            }
        }
    }

    SettingsScreenScaffoldView(
        state = state,
        onAction = viewModel::processAction
    )
}

@Composable
private fun SettingsScreenScaffoldView(
    state: SettingState,
    onAction: (SettingAction) -> Unit,
) {
    if (state.showThemeSelection) {
        ThemeDialogView {
            onAction.invoke(SettingAction.DismissThemeSelection)
        }
    }

    Scaffold(
        topBar = {
            TopNavigationBar(
                onClick = {
                    onAction.invoke(SettingAction.ClosePage)
                },
                title = stringResource(Res.string.settings),
            )
        },
    ) { innerPadding ->
        SettingsScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            selectedCurrency = state.currency,
            theme = state.theme,
            onAction = onAction
        )
    }
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    selectedCurrency: Currency,
    theme: Theme? = null,
    onAction: (SettingAction) -> Unit,
) {
    Column(modifier = modifier) {
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.ShowThemeSelection)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.theme),
            description = if (theme != null) {
                (stringResource(resource = theme.titleResId))
            } else {
                stringResource(resource = Res.string.system_default)
            },
            imageVector = Icons.Outlined.Palette,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenCurrencyEdit)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.currency),
            description = "${selectedCurrency.name}(${selectedCurrency.symbol})",
            imageVector = Icons.Outlined.Payments,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenNotification)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.reminder_notification),
            description = stringResource(resource = Res.string.selected_daily_reminder_time),
            imageVector = Icons.Outlined.EditNotifications,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenExport)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.export),
            description = stringResource(resource = Res.string.export_message),
            imageVector = Icons.Outlined.Upload,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenRateUs)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.rate_us),
            description = stringResource(resource = Res.string.rate_us_message),
            imageVector = Icons.Outlined.RateReview,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenShareApp)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.share),
            description = stringResource(resource = Res.string.recommend_to_friend),
            imageVector = Icons.Outlined.Share,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenAdvancedSettings)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.advanced),
            description = stringResource(resource = Res.string.advanced_config_message),
            imageVector = Icons.Outlined.SettingsApplications,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(SettingAction.OpenAboutUs)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = expensemanager.feature.about4mp.generated.resources.Res.string.about_us),
            description = stringResource(resource = Res.string.about_the_app_information),
            imageVector = Icons.Outlined.Info,
        )
    }
}

@Composable
private fun SettingsItem(
    title: String,
    description: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Icon(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            imageVector = imageVector,
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

@AppPreviewsLightAndDarkMode
@Composable
fun SettingsScreenItemPreview() {
    ExpenseManagerTheme {
        SettingsItem(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.theme),
            description = stringResource(resource = Res.string.system_default),
            imageVector = Icons.Outlined.Upload,
        )
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun SettingsScreenPreview() {
    ExpenseManagerTheme {
        SettingsScreenScaffoldView(
            state = SettingState(
                currency = Currency("$", "US Dollar"),
                theme = null,
                showThemeSelection = false
            ),
            onAction = {}
        )
    }
}
