package com.naveenapps.expensemanager.feature.about

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
import androidx.compose.material.icons.outlined.FolderOpen
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.naveenapps.expensemanager.core.designsystem.AppPreviewsLightAndDarkMode
import com.naveenapps.expensemanager.core.designsystem.ui.components.TopNavigationBar
import com.naveenapps.expensemanager.core.designsystem.ui.theme.ExpenseManagerTheme
import com.naveenapps.expensemanager.core.repository.ShareRepository
import expensemanager.feature.about4mp.generated.resources.Res
import expensemanager.feature.about4mp.generated.resources.about_us
import expensemanager.feature.about4mp.generated.resources.app_version
import expensemanager.feature.about4mp.generated.resources.ic_mail
import expensemanager.feature.about4mp.generated.resources.licenses
import expensemanager.feature.about4mp.generated.resources.privacy_policy
import expensemanager.feature.about4mp.generated.resources.terms_and_conditions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AboutScreen(
    shareRepository: ShareRepository,
    viewModel: AboutUsViewModel = koinViewModel()
) {
    AboutUsScreenScaffoldView(
        onAction = {
            when (it) {
                AboutAction.OpenTerms -> {
                    shareRepository.openTerms()
                }

                AboutAction.OpenPrivacy -> {
                    shareRepository.openPrivacy()
                }

                AboutAction.Mail -> {
                    shareRepository.sendEmail()
                }

                AboutAction.OpenLicense -> {
                    println("Not yet implemented")
                }

                else -> {
                    viewModel.processAction(it)
                }
            }
        }
    )
}

@Composable
private fun AboutUsScreenScaffoldView(
    onAction: (AboutAction) -> Unit,
) {

    Scaffold(
        topBar = {
            TopNavigationBar(
                onClick = { onAction.invoke(AboutAction.ClosePage) },
                title = stringResource(Res.string.about_us),
            )
        },
    ) { innerPadding ->
        AboutUsScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding),
            onAction
        )
    }
}

@Composable
private fun AboutUsScreenContent(
    modifier: Modifier = Modifier,
    onAction: (AboutAction) -> Unit,
) {
    Column(modifier = modifier) {
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(AboutAction.OpenTerms)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.terms_and_conditions),
            icon = Icons.Outlined.Policy,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(AboutAction.OpenPrivacy)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.privacy_policy),
            icon = Icons.Outlined.PrivacyTip,
        )
        SettingsItem(
            modifier = Modifier
                .clickable {
                    onAction.invoke(AboutAction.OpenLicense)
                }
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth(),
            title = stringResource(resource = Res.string.licenses),
            icon = Icons.Outlined.FolderOpen,
        )

        DeveloperInfoView(onAction)
    }
}

@Composable
private fun DeveloperInfoView(
    onAction: (AboutAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
        ) {
            IconButton(onClick = {
                onAction.invoke(AboutAction.Mail)
            }) {
                Icon(
                    painter = painterResource(resource = Res.drawable.ic_mail),
                    contentDescription = "",
                )
            }
        }
        Text(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp),
            text = stringResource(
                resource = Res.string.app_version,
                "0x00",
            ),
        )
    }
}

@Composable
private fun SettingsItem(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    description: String? = null,
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
            if (description?.isNotBlank() == true) {
                Text(text = description, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

@AppPreviewsLightAndDarkMode
@Composable
fun AboutUsPreview() {
    ExpenseManagerTheme {
        AboutUsScreenScaffoldView {

        }
    }
}
